package com.lexwilliam.feature_add.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lexwilliam.domain.model.Test
import com.lexwilliam.feature_add.R

class FlashcardListAdapter(private val data: List<Test>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
        private const val TYPE_FOOTER = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val inflatedView : RecyclerView.ViewHolder = when (viewType) {
            TYPE_ITEM -> ItemViewHolder(layoutInflater.inflate(R.layout.flashcard_edit_card, parent,false))
            TYPE_HEADER -> HeaderViewHolder(layoutInflater.inflate(R.layout.flashcard_edit_card, parent,false))
            else -> FooterViewHolder(layoutInflater.inflate(R.layout.flashcard_edit_card, parent,false))
        }

        return inflatedView
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> holder.bind()
            is HeaderViewHolder -> holder.bind()
            is FooterViewHolder -> holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_HEADER;
        }
        else if (position == data.size) {
            return TYPE_FOOTER
        }
        return TYPE_ITEM;
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class HeaderViewHolder(view: View): RecyclerView.ViewHolder(view) { fun bind() {} }

class FooterViewHolder(view: View): RecyclerView.ViewHolder(view) { fun bind() {} }

class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) { fun bind() {} }