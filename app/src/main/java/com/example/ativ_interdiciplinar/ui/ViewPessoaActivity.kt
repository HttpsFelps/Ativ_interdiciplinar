package com.example.ativ_interdiciplinar.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ativ_interdiciplinar.R
import com.example.ativ_interdiciplinar.data.Pessoa
import com.example.ativ_interdiciplinar.viewmodel.PessoaViewModel

class ViewPessoaActivity : AppCompatActivity() {

    private val viewModel: PessoaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pessoa)

        val tvNome: TextView = findViewById(R.id.tvNome)
        val tvEmail: TextView = findViewById(R.id.tvEmail)
        val tvTelefone: TextView = findViewById(R.id.tvTelefone)
        val tvCpf: TextView = findViewById(R.id.tvCpf)
        val tvIdade: TextView = findViewById(R.id.tvIdade)
        val tvEndereco: TextView = findViewById(R.id.tvEndereco)
        val btnClose: Button = findViewById(R.id.btnClose)

        val id = intent.getIntExtra("pessoa_id", -1)
        if (id == -1) {
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        viewModel.getPessoaById(id) { pessoa ->
            runOnUiThread {
                if (pessoa != null) {
                    bind(pessoa, tvNome, tvEmail, tvTelefone, tvCpf, tvIdade, tvEndereco)
                } else {
                    Toast.makeText(this, "Pessoa não encontrada", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

        btnClose.setOnClickListener { finish() }
    }

    private fun bind(p: Pessoa, tvNome: TextView, tvEmail: TextView, tvTelefone: TextView, tvCpf: TextView, tvIdade: TextView, tvEndereco: TextView) {
        tvNome.text = p.nome ?: "-"
        tvEmail.text = p.email ?: "-"
        tvTelefone.text = p.telefone ?: "-"
        tvCpf.text = p.cpf ?: "-"
        tvIdade.text = p.idade?.toString() ?: "-"
        tvEndereco.text = getString(
            R.string.form_address_format,
            p.logradouro ?: "",
            p.numero?.toString() ?: "",
            p.complemento ?: ""
        )
    }
}
