package com.lexwilliam.add.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lexwilliam.add.databinding.HeaderAddBinding
import com.lexwilliam.core.model.TitlePresentation
import timber.log.Timber

class HeaderAdapter(
    private val title: TitlePresentation,
    private val onIconClicked: () -> Unit
): RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder =
        HeaderViewHolder(
            HeaderAddBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind(title)
    }

    override fun getItemCount(): Int {
        return 1
    }

    inner class HeaderViewHolder(private val binding: HeaderAddBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(title: TitlePresentation) {
            binding.title = title
            binding.editPackNameTxt.apply {
                setEndIconOnClickListener { onIconClicked() }
            }
        }
    }

    fun setImageUri(imageUri: Uri) {
        title.imageUri = imageUri
        notifyDataSetChanged()
    }
}