package com.example.ativ_interdiciplinar.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ativ_interdiciplinar.R
import com.example.ativ_interdiciplinar.databinding.ActivityUpdatePessoaBinding
import com.example.ativ_interdiciplinar.viewmodel.PessoaViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class UpdatePessoaActivity : AppCompatActivity() {

    private val viewModel: PessoaViewModel by viewModels()
    private lateinit var binding: ActivityUpdatePessoaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = ActivityUpdatePessoaBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val etNome: TextInputEditText = binding.etNome
            val etEmail: TextInputEditText = binding.etEmail
            val etTelefone: TextInputEditText = binding.etTelefone
            val etCpf: TextInputEditText = binding.etCpf
            val etIdade: TextInputEditText = binding.etIdade
            val btnUpdate: MaterialButton = binding.btnUpdate
            val btnCancel: MaterialButton = binding.btnCancel

            val id = intent.getIntExtra("pessoa_id", -1)
            if (id == -1) {
                Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show()
                finish()
                return
            }

            // Setup toolbar
            binding.toolbar.setNavigationOnClickListener {
                finish()
            }

            viewModel.getPessoaById(id) { pessoa ->
                runOnUiThread {
                    if (pessoa != null) {
                        etNome.setText(pessoa.nome)
                        etEmail.setText(pessoa.email)
                        etTelefone.setText(pessoa.telefone)
                        etCpf.setText(pessoa.cpf)
                        etIdade.setText(pessoa.idade?.toString() ?: "")
                    } else {
                        Toast.makeText(this@UpdatePessoaActivity, "Pessoa não encontrada", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }

            btnCancel.setOnClickListener {
                finish()
            }

            btnUpdate.setOnClickListener {
                val payload = mapOf(
                    "nome" to etNome.text.toString(),
                    "email" to etEmail.text.toString(),
                    "telefone" to etTelefone.text.toString(),
                    "cpf" to etCpf.text.toString(),
                    "idade" to (etIdade.text.toString().toIntOrNull() ?: 0)
                )

                viewModel.updatePessoa(id, payload) {
                    runOnUiThread {
                        Toast.makeText(this@UpdatePessoaActivity, "Atualizado com sucesso", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Erro ao carregar: ${e.message}", Toast.LENGTH_LONG).show()
            android.util.Log.e("UpdatePessoaActivity", "Erro: ", e)
            finish()
        }
    }
}
