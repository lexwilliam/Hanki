package com.lexwilliam.packs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lexwilliam.core.model.PackInfoPresentation
import com.lexwilliam.packs.databinding.PackCardBinding

class PackListAdapter(
    private val packs: List<PackInfoPresentation>,
    private val onItemClicked: (PackInfoPresentation) -> Unit
    ):
    RecyclerView.Adapter<PackListAdapter.PackListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackListViewHolder =
        PackListViewHolder(
            PackCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PackListViewHolder, position: Int) {
        val item = packs[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onItemClicked(item) }
    }

    override fun getItemCount(): Int {
        return packs.size
    }

    inner class PackListViewHolder(private val binding: PackCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(packInfo: PackInfoPresentation) {
            binding.packInfo = packInfo
        }
    }
}