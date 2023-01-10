package com.lexwilliam.feature_pack_detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lexwilliam.core.model.FlashcardPresentation
import com.lexwilliam.core.model.PackPresentation
import com.lexwilliam.feature_pack_detail.databinding.HeaderDetailBinding

class HeaderDetailAdapter(
    private val pack: PackPresentation
): RecyclerView.Adapter<HeaderDetailAdapter.HeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder =
        HeaderViewHolder(
            HeaderDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind(pack)
    }

    override fun getItemCount(): Int {
        return 1
    }

    inner class HeaderViewHolder(private val binding: HeaderDetailBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pack: PackPresentation) {
            binding.pack = pack
        }
    }
}