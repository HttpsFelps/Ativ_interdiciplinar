package com.example.ativ_interdiciplinar.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ativ_interdiciplinar.databinding.ActivityViewPessoaBinding
import com.example.ativ_interdiciplinar.data.Pessoa
import com.example.ativ_interdiciplinar.viewmodel.PessoaViewModel

class ViewPessoaActivity : AppCompatActivity() {

    private val viewModel: PessoaViewModel by viewModels()
    private lateinit var binding: ActivityViewPessoaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = ActivityViewPessoaBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val tvNome = binding.tvNome
            val tvEmail = binding.tvEmail
            val tvTelefone = binding.tvTelefone
            val tvCpf = binding.tvCpf
            val tvIdade = binding.tvIdade
            val btnClose = binding.btnClose

            // Setup toolbar
            binding.toolbar.setNavigationOnClickListener {
                finish()
            }

            val id = intent.getIntExtra("pessoa_id", -1)
            if (id == -1) {
                Toast.makeText(this@ViewPessoaActivity, "ID inválido", Toast.LENGTH_SHORT).show()
                finish()
                return
            }

            viewModel.getPessoaById(id) { pessoa ->
                runOnUiThread {
                    if (pessoa != null) {
                        tvNome.text = pessoa.nome ?: "—"
                        tvEmail.text = pessoa.email ?: "—"
                        tvTelefone.text = pessoa.telefone ?: "—"
                        tvCpf.text = pessoa.cpf ?: "—"
                        tvIdade.text = if (pessoa.idade != null) "${pessoa.idade} anos" else "—"
                    } else {
                        Toast.makeText(this@ViewPessoaActivity, "Pessoa não encontrada", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }

            btnClose.setOnClickListener { finish() }
        } catch (e: Exception) {
            Toast.makeText(this, "Erro ao carregar: ${e.message}", Toast.LENGTH_LONG).show()
            android.util.Log.e("ViewPessoaActivity", "Erro: ", e)
            finish()
        }
    }
}
