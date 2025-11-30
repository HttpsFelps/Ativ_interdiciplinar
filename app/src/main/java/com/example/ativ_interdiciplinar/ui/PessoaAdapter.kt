package com.example.ativ_interdiciplinar.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.ativ_interdiciplinar.R
import com.example.ativ_interdiciplinar.data.Pessoa

class PessoaAdapter(
    private var items: List<Pessoa> = listOf(),
    private val onClick: (Pessoa, Int) -> Unit = { _, _ -> }
) : RecyclerView.Adapter<PessoaAdapter.ViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    fun update(newItems: List<Pessoa>) {
        items = newItems
        notifyDataSetChanged()
    }

    fun selectPosition(position: Int) {
        val previous = selectedPosition
        selectedPosition = position
        if (previous != RecyclerView.NO_POSITION) notifyItemChanged(previous)
        notifyItemChanged(position)
    }

    fun clearSelection() {
        val previous = selectedPosition
        selectedPosition = RecyclerView.NO_POSITION
        if (previous != RecyclerView.NO_POSITION) notifyItemChanged(previous)
    }

    fun getSelectedPessoa(): Pessoa? {
        return if (selectedPosition != RecyclerView.NO_POSITION && selectedPosition < items.size) {
            items[selectedPosition]
        } else null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pessoa, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPosition = holder.bindingAdapterPosition
        if (currentPosition == RecyclerView.NO_POSITION) return
        
        val p = items[currentPosition]
        holder.tvNome.text = p.nome ?: "—"
        holder.tvEmail.text = p.email ?: "—"
        holder.tvTelefone.text = p.telefone ?: "—"

        // highlight selected
        val card = holder.itemView as? CardView
        if (currentPosition == selectedPosition) {
            card?.setCardBackgroundColor(Color.parseColor("#FFF3E0"))
        } else {
            card?.setCardBackgroundColor(Color.WHITE)
        }

        holder.itemView.setOnClickListener {
            val adapterPos = holder.bindingAdapterPosition
            if (adapterPos != RecyclerView.NO_POSITION) {
                val prev = selectedPosition
                selectedPosition = adapterPos
                if (prev != RecyclerView.NO_POSITION) notifyItemChanged(prev)
                notifyItemChanged(adapterPos)
                onClick(p, adapterPos)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNome: TextView = view.findViewById(R.id.tvNome)
        val tvEmail: TextView = view.findViewById(R.id.tvEmail)
        val tvTelefone: TextView = view.findViewById(R.id.tvTelefone)
    }
}