package com.example.ativ_interdiciplinar.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ativ_interdiciplinar.R
import com.example.ativ_interdiciplinar.viewmodel.PessoaViewModel

class UpdatePessoaActivity : AppCompatActivity() {

    private val viewModel: PessoaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_pessoa)

        val etNome: EditText = findViewById(R.id.etNome)
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etTelefone: EditText = findViewById(R.id.etTelefone)
        val etCpf: EditText = findViewById(R.id.etCpf)
        val etIdade: EditText = findViewById(R.id.etIdade)
        val etLogradouro: EditText = findViewById(R.id.etLogradouro)
        val etNumero: EditText = findViewById(R.id.etNumero)
        val etComplemento: EditText = findViewById(R.id.etComplemento)
        val btnSubmit: Button = findViewById(R.id.btnSubmit)

        val id = intent.getIntExtra("pessoa_id", -1)
        if (id == -1) {
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        viewModel.getPessoaById(id) { pessoa ->
            runOnUiThread {
                if (pessoa != null) {
                    etNome.setText(pessoa.nome)
                    etEmail.setText(pessoa.email)
                    etTelefone.setText(pessoa.telefone)
                    etCpf.setText(pessoa.cpf)
                    etIdade.setText(pessoa.idade?.toString() ?: "")
                    etLogradouro.setText(pessoa.logradouro)
                    etNumero.setText(pessoa.numero?.toString() ?: "")
                    etComplemento.setText(pessoa.complemento)
                } else {
                    Toast.makeText(this, "Pessoa não encontrada", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

        btnSubmit.setOnClickListener {
            val payload = mapOf(
                "nome" to etNome.text.toString(),
                "email" to etEmail.text.toString(),
                "telefone" to etTelefone.text.toString(),
                "cpf" to etCpf.text.toString(),
                "idade" to (etIdade.text.toString().toIntOrNull() ?: 0),
                "logradouro" to etLogradouro.text.toString(),
                "numero" to (etNumero.text.toString().toIntOrNull() ?: 0),
                "complemento" to etComplemento.text.toString()
            )

            viewModel.updatePessoa(id, payload) {
                runOnUiThread {
                    Toast.makeText(this, "Atualizado com sucesso", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
