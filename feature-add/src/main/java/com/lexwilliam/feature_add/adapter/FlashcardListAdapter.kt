package com.lexwilliam.feature_add.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lexwilliam.domain.model.Flashcard
import com.lexwilliam.feature_add.R
import timber.log.Timber


class FlashcardListAdapter(private val data: List<Flashcard>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
        private const val TYPE_FOOTER = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        Timber.d(viewType.toString())

        val inflatedView : RecyclerView.ViewHolder = when (viewType) {
            TYPE_ITEM -> ItemViewHolder(layoutInflater.inflate(R.layout.flashcard_edit_card, parent,false))
            TYPE_HEADER -> HeaderViewHolder(layoutInflater.inflate(R.layout.header_add, parent,false))
            TYPE_FOOTER -> FooterViewHolder(layoutInflater.inflate(R.layout.footer_add, parent,false))
            else -> FooterViewHolder(layoutInflater.inflate(R.layout.footer_add, parent,false))
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
        else if (position == data.size + 1) {
            return TYPE_FOOTER
        }
        return TYPE_ITEM;
    }

    override fun getItemCount(): Int {
        if (data.isEmpty()) {
            return 1;
        }
        return data.size + 2
    }

    inner class HeaderViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind() {}
    }

    inner class FooterViewHolder(view: View): RecyclerView.ViewHolder(view) { fun bind() {} }

    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind() {}
    }
}