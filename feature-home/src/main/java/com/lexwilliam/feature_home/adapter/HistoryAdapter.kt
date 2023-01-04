package com.lexwilliam.feature_home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lexwilliam.core.model.PackInfoPresentation
import com.lexwilliam.feature_home.R
import com.lexwilliam.feature_home.databinding.HistoryCardBinding

class HistoryAdapter(private val packs: List<PackInfoPresentation>):
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder =
        HistoryViewHolder(
            HistoryCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(packs[position])
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