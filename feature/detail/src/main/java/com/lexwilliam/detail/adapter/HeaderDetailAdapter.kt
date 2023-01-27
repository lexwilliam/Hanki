package com.lexwilliam.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lexwilliam.core.model.PackPresentation
import com.lexwilliam.detail.databinding.HeaderDetailBinding

class HeaderDetailAdapter(
    private val pack: PackPresentation,
    private val onFlashcardClick: (PackPresentation) -> Unit
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
            binding.flashcardBtn.setOnClickListener { onFlashcardClick(pack) }
        }
    }
}