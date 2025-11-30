package com.example.ativ_interdiciplinar.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ativ_interdiciplinar.databinding.ActivityCreatePessoaBinding
import com.example.ativ_interdiciplinar.viewmodel.PessoaViewModel
import com.google.android.material.textfield.TextInputEditText

class CreatePessoaActivity : AppCompatActivity() {

    private val viewModel: PessoaViewModel by viewModels()
    private lateinit var binding: ActivityCreatePessoaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = ActivityCreatePessoaBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val etNome: TextInputEditText = binding.etNome
            val etEmail: TextInputEditText = binding.etEmail
            val etTelefone: TextInputEditText = binding.etTelefone
            val etCpf: TextInputEditText = binding.etCpf
            val etIdade: TextInputEditText = binding.etIdade
            val btnSave = binding.btnSave
            val btnCancel = binding.btnCancel

            // Setup toolbar
            binding.toolbar.setNavigationOnClickListener {
                finish()
            }

            btnCancel.setOnClickListener {
                finish()
            }

            btnSave.setOnClickListener {
                // Validar campos vazios
                if (etNome.text.isNullOrEmpty() || etEmail.text.isNullOrEmpty()) {
                    Toast.makeText(this@CreatePessoaActivity, "Preencha Nome e Email", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val payload = mapOf(
                    "nome" to etNome.text.toString(),
                    "email" to etEmail.text.toString(),
                    "telefone" to etTelefone.text.toString(),
                    "cpf" to etCpf.text.toString(),
                    "idade" to (etIdade.text.toString().toIntOrNull() ?: 0)
                )

                viewModel.createPessoa(payload) {
                    runOnUiThread {
                        Toast.makeText(this@CreatePessoaActivity, "Criada com sucesso", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Erro ao carregar: ${e.message}", Toast.LENGTH_LONG).show()
            android.util.Log.e("CreatePessoaActivity", "Erro: ", e)
            finish()
        }
    }
}
