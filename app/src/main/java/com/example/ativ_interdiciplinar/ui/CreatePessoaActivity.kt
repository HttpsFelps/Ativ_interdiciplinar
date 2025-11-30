package com.example.ativ_interdiciplinar.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ativ_interdiciplinar.R
import com.example.ativ_interdiciplinar.viewmodel.PessoaViewModel

class CreatePessoaActivity : AppCompatActivity() {

    private val viewModel: PessoaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pessoa)

        val etNome: EditText = findViewById(R.id.etNome)
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etTelefone: EditText = findViewById(R.id.etTelefone)
        val etCpf: EditText = findViewById(R.id.etCpf)
        val etIdade: EditText = findViewById(R.id.etIdade)
        val etLogradouro: EditText = findViewById(R.id.etLogradouro)
        val etNumero: EditText = findViewById(R.id.etNumero)
        val etComplemento: EditText = findViewById(R.id.etComplemento)
        val btnSubmit: Button = findViewById(R.id.btnSubmit)

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

            viewModel.createPessoa(payload) {
                runOnUiThread {
                    Toast.makeText(this, "Criada com sucesso", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
