package com.lexwilliam.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lexwilliam.core.model.PackInfoPresentation
import com.lexwilliam.home.databinding.HistoryCardBinding

class HistoryAdapter(
    private val packs: List<PackInfoPresentation>,
    private val onItemClicked: (PackInfoPresentation) -> Unit
    ): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder =
        HistoryViewHolder(
            HistoryCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = packs[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onItemClicked(item) }
    }

    override fun getItemCount(): Int {
        return packs.size
    }

    inner class HistoryViewHolder(private val binding: HistoryCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(packInfo: PackInfoPresentation) {
            binding.packInfo = packInfo
        }
    }
}