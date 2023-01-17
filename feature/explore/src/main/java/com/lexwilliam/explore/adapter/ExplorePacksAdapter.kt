package com.lexwilliam.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lexwilliam.core.model.PackPresentation
import com.lexwilliam.explore.databinding.PackColumnCardBinding

class ExplorePacksAdapter(
    private val packs: List<PackPresentation>,
    private val onItemClicked: (PackPresentation) -> Unit
): RecyclerView.Adapter<ExplorePacksAdapter.PackListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackListViewHolder =
        PackListViewHolder(
            PackColumnCardBinding.inflate(
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

    inner class PackListViewHolder(private val binding: PackColumnCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pack: PackPresentation) {
            binding.pack = pack
        }
    }
}