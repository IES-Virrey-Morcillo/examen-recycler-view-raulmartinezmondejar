package com.example.reciclerviewmontes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reciclerviewmontes.Monte
import com.example.reciclerviewmontes.databinding.ItemMonteBinding

class MontesAdapter (
    private val MontesList: MutableList<Monte>,
    private val onClickListener: (Monte) -> Unit,
    private val onClickDelete: (Int) -> Unit
) : RecyclerView.Adapter<MontesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MontesViewHolder {
        val binding = ItemMonteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MontesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MontesViewHolder, position: Int) {
        val monte = MontesList[position]
        holder.render(monte, onClickDelete)
        holder.itemView.setOnClickListener { onClickListener(monte) }
    }

    override fun getItemCount(): Int = MontesList.size

    fun filterByName(movies:MutableList<Monte>)
    {
        MontesList.clear()
        MontesList.addAll(movies)
        notifyDataSetChanged()
    }

}