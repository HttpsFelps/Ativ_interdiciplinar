package com.example.ativ_interdiciplinar.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.ativ_interdiciplinar.R
import com.example.ativ_interdiciplinar.data.Pessoa
import com.example.ativ_interdiciplinar.viewmodel.PessoaViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: PessoaViewModel by viewModels()

    private lateinit var adapter: PessoaAdapter
    private lateinit var rvPessoas: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var btnCreate: Button
    private lateinit var btnGetById: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    private var pessoaSelecionada: Pessoa? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupAdapter()
        setupClickListeners()
        observeViewModel()

        viewModel.loadPessoas()
    }

    private fun initializeViews() {
        rvPessoas = findViewById(R.id.rvPessoas)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        btnCreate = findViewById(R.id.btnCreate)
        btnGetById = findViewById(R.id.btnGetById)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setupAdapter() {
        adapter = PessoaAdapter(onClick = { pessoa, position ->
            pessoaSelecionada = pessoa
            adapter.selectPosition(position)
            Toast.makeText(this, "Selecionado: ${pessoa.nome}", Toast.LENGTH_SHORT).show()
        })

        rvPessoas.layoutManager = LinearLayoutManager(this)
        rvPessoas.adapter = adapter
    }

    private fun setupClickListeners() {
        swipeRefresh.setOnRefreshListener {
            pessoaSelecionada = null
            adapter.selectPosition(RecyclerView.NO_POSITION)
            viewModel.loadPessoas()
        }

        btnCreate.setOnClickListener {
            startActivity(Intent(this, CreatePessoaActivity::class.java))
        }

        btnGetById.setOnClickListener {
            val p = pessoaSelecionada
            if (p != null) {
                val intent = Intent(this, ViewPessoaActivity::class.java)
                intent.putExtra("pessoa_id", p.id)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Selecione uma pessoa na lista", Toast.LENGTH_SHORT).show()
            }
        }

        btnUpdate.setOnClickListener {
            val p = pessoaSelecionada
            if (p != null) {
                val intent = Intent(this, UpdatePessoaActivity::class.java)
                intent.putExtra("pessoa_id", p.id)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Selecione uma pessoa na lista", Toast.LENGTH_SHORT).show()
            }
        }

        btnDelete.setOnClickListener {
            val p = pessoaSelecionada
            if (p != null) {
                viewModel.deletePessoa(p.id) {
                    runOnUiThread {
                        Toast.makeText(this, "Deletado com sucesso", Toast.LENGTH_SHORT).show()
                        pessoaSelecionada = null
                        adapter.selectPosition(RecyclerView.NO_POSITION)
                    }
                }
            } else {
                Toast.makeText(this, "Selecione uma pessoa na lista", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.pessoas.collectLatest { list ->
                adapter.update(list)
                swipeRefresh.isRefreshing = false
            }
        }

        lifecycleScope.launch {
            viewModel.error.collectLatest { err ->
                if (!err.isNullOrBlank()) {
                    Toast.makeText(this@MainActivity, err, Toast.LENGTH_LONG).show()
                }
                swipeRefresh.isRefreshing = false
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        // Recarrega lista ao voltar de outra tela (create/update)
        viewModel.loadPessoas()
    }
}
