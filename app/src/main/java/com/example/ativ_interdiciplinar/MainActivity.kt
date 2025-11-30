package com.example.ativ_interdiciplinar

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ativ_interdiciplinar.databinding.ActivityMainBinding
import com.example.ativ_interdiciplinar.ui.PessoaAdapter
import com.example.ativ_interdiciplinar.viewmodel.PessoaViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: PessoaViewModel by viewModels()
    private val adapter = PessoaAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura RecyclerView
        binding.rvPessoas.layoutManager = LinearLayoutManager(this)
        binding.rvPessoas.adapter = adapter

        // Observa dados vindos da API
        lifecycleScope.launch {
            viewModel.pessoas.collect { lista ->
                adapter.update(lista)
            }
        }

        // Chama API para carregar os dados
        viewModel.loadPessoas()
    }
}
