package com.example.ativ_interdiciplinar.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ativ_interdiciplinar.R
import com.example.ativ_interdiciplinar.viewmodel.PessoaViewModel

class DeletePessoaActivity : AppCompatActivity() {

    private val viewModel: PessoaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_pessoa)

        val btnConfirm: Button = findViewById(R.id.btnConfirm)
        val btnCancel: Button = findViewById(R.id.btnCancel)

        val id = intent.getIntExtra("pessoa_id", -1)
        if (id == -1) {
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        btnConfirm.setOnClickListener {
            viewModel.deletePessoa(id) {
                runOnUiThread {
                    Toast.makeText(this, "Deletado com sucesso", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

        btnCancel.setOnClickListener { finish() }
    }
}
