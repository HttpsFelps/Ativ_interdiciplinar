package com.example.ativ_interdiciplinar

import android.os.Bundle
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ativ_interdiciplinar.databinding.ActivityMainBinding
import com.example.ativ_interdiciplinar.ui.PessoaAdapter
import com.example.ativ_interdiciplinar.viewmodel.PessoaViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: PessoaViewModel by viewModels()
    private lateinit var adapter: PessoaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PessoaAdapter { pessoa, position ->
            // Ao clicar no item — seleciona e habilita botões
            adapter.selectPosition(position)
            onPessoaSelected(pessoa, position)
        }

        // Configura RecyclerView
        binding.rvPessoas.layoutManager = LinearLayoutManager(this)
        binding.rvPessoas.adapter = adapter

        // Observa dados vindos da API
        lifecycleScope.launch {
            viewModel.pessoas.collectLatest { lista ->
                adapter.update(lista)
                // Ao atualizar a lista, limpar seleção e desabilitar ações
                adapter.clearSelection()
                binding.btnGetById.isEnabled = false
                binding.btnUpdate.isEnabled = false
                binding.btnDelete.isEnabled = false
            }
        }

        // Swipe to refresh
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadPessoas()
            binding.swipeRefresh.isRefreshing = false
        }

        // Label / subtitle
        binding.toolbar.subtitle = "App de exemplo: CRUD de Pessoas via API"

        // Botões de navegação para cada ação
        binding.btnCreate.setOnClickListener {
            startActivity(Intent(this, com.example.ativ_interdiciplinar.ui.CreatePessoaActivity::class.java))
        }

        // Inicialmente não é possível visualizar/atualizar/deletar sem seleção
        binding.btnGetById.isEnabled = false
        binding.btnUpdate.isEnabled = false
        binding.btnDelete.isEnabled = false

        binding.btnGetById.setOnClickListener {
            val selected = getSelectedPessoa()
            if (selected == null) {
                android.widget.Toast.makeText(this, "Selecione uma pessoa na lista", android.widget.Toast.LENGTH_SHORT).show()
            } else {
                val i = Intent(this, com.example.ativ_interdiciplinar.ui.ViewPessoaActivity::class.java)
                i.putExtra("pessoa_id", selected.id)
                startActivity(i)
            }
        }

        binding.btnUpdate.setOnClickListener {
            val selected = getSelectedPessoa()
            if (selected == null) {
                android.widget.Toast.makeText(this, "Selecione uma pessoa na lista", android.widget.Toast.LENGTH_SHORT).show()
            } else {
                val i = Intent(this, com.example.ativ_interdiciplinar.ui.UpdatePessoaActivity::class.java)
                i.putExtra("pessoa_id", selected.id)
                startActivity(i)
            }
        }

        binding.btnDelete.setOnClickListener {
            val selected = getSelectedPessoa()
            if (selected == null) {
                android.widget.Toast.makeText(this, "Selecione uma pessoa na lista", android.widget.Toast.LENGTH_SHORT).show()
            } else {
                val i = Intent(this, com.example.ativ_interdiciplinar.ui.DeletePessoaActivity::class.java)
                i.putExtra("pessoa_id", selected.id)
                startActivity(i)
            }
        }

        // Chama API para carregar os dados
        viewModel.loadPessoas()
    }

    override fun onResume() {
        super.onResume()
        // Sempre recarrega a lista ao voltar para a tela principal
        viewModel.loadPessoas()
    }

    private fun getSelectedPessoa(): com.example.ativ_interdiciplinar.data.Pessoa? {
        return adapter.getSelectedPessoa()
    }

    private fun onPessoaSelected(pessoa: com.example.ativ_interdiciplinar.data.Pessoa, position: Int) {
        // habilita botões quando um item é selecionado
        binding.btnGetById.isEnabled = true
        binding.btnUpdate.isEnabled = true
        binding.btnDelete.isEnabled = true
    }
}
