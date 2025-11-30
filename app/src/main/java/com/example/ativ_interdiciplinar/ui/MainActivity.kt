package com.example.ativ_interdiciplinar.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ativ_interdiciplinar.R
import com.example.ativ_interdiciplinar.viewmodel.PessoaViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: PessoaViewModel by viewModels()

    private lateinit var adapter: PessoaAdapter
    private lateinit var rvPessoas: RecyclerView
    private lateinit var tvError: TextView
    private lateinit var btnRefresh: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvPessoas = findViewById(R.id.rvPessoas)
        tvError = findViewById(R.id.tvError)
        btnRefresh = findViewById(R.id.btnRefresh)

        adapter = PessoaAdapter(onClick = { _ ->
            // clique em item -> exemplo: deletar ou abrir detalhe
            // exibir Toast, etc. Aqui só um placeholder
        })

        rvPessoas.layoutManager = LinearLayoutManager(this)
        rvPessoas.adapter = adapter

        btnRefresh.setOnClickListener {
            viewModel.loadPessoas()
        }

        observeViewModel()

        // Carrega dados inicial
        viewModel.loadPessoas()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.pessoas.collectLatest { list ->
                adapter.update(list)
            }
        }

        lifecycleScope.launch {
            viewModel.error.collectLatest { err ->
                if (err.isNullOrBlank()) {
                    tvError.visibility = View.GONE
                } else {
                    tvError.visibility = View.VISIBLE
                    tvError.text = err
                }
            }
        }
    }
}