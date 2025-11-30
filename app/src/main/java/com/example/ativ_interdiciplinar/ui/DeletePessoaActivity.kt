package com.example.ativ_interdiciplinar.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ativ_interdiciplinar.databinding.ActivityDeletePessoaBinding
import com.example.ativ_interdiciplinar.viewmodel.PessoaViewModel

class DeletePessoaActivity : AppCompatActivity() {

    private val viewModel: PessoaViewModel by viewModels()
    private lateinit var binding: ActivityDeletePessoaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = ActivityDeletePessoaBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val btnDelete = binding.btnDelete
            val btnCancel = binding.btnCancel
            val tvNome = binding.tvNome
            val tvEmail = binding.tvEmail

            // Setup toolbar
            binding.toolbar.setNavigationOnClickListener {
                finish()
            }

            val id = intent.getIntExtra("pessoa_id", -1)
            if (id == -1) {
                Toast.makeText(this@DeletePessoaActivity, "ID inválido", Toast.LENGTH_SHORT).show()
                finish()
                return
            }

            // Carrega dados da pessoa para exibição
            viewModel.getPessoaById(id) { pessoa ->
                runOnUiThread {
                    if (pessoa != null) {
                        tvNome.text = pessoa.nome ?: "—"
                        tvEmail.text = pessoa.email ?: "—"
                    } else {
                        Toast.makeText(this@DeletePessoaActivity, "Pessoa não encontrada", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }

            btnDelete.setOnClickListener {
                viewModel.deletePessoa(id) {
                    runOnUiThread {
                        Toast.makeText(this@DeletePessoaActivity, "Deletado com sucesso", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }

            btnCancel.setOnClickListener { finish() }
        } catch (e: Exception) {
            Toast.makeText(this, "Erro ao carregar: ${e.message}", Toast.LENGTH_LONG).show()
            android.util.Log.e("DeletePessoaActivity", "Erro: ", e)
            finish()
        }
    }
}
