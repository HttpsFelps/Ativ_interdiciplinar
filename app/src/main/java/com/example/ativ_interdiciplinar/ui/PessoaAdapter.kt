package com.example.ativ_interdiciplinar.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ativ_interdiciplinar.R
import com.example.ativ_interdiciplinar.data.Pessoa

class PessoaAdapter(
    private var items: List<Pessoa> = listOf(),
    private val onClick: (Pessoa) -> Unit = {}
) : RecyclerView.Adapter<PessoaAdapter.ViewHolder>() {

    fun update(newItems: List<Pessoa>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pessoa, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = items[position]
        holder.tvNome.text = p.nome ?: "—"
        holder.tvEmail.text = p.email ?: "—"
        holder.tvTelefone.text = p.telefone ?: "—"
        holder.itemView.setOnClickListener { onClick(p) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNome: TextView = view.findViewById(R.id.tvNome)
        val tvEmail: TextView = view.findViewById(R.id.tvEmail)
        val tvTelefone: TextView = view.findViewById(R.id.tvTelefone)
    }
}