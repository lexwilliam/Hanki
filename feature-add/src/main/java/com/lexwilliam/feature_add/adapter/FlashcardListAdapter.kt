package com.lexwilliam.feature_add.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lexwilliam.domain.model.Flashcard
import com.lexwilliam.feature_add.R


class FlashcardListAdapter:
    RecyclerView.Adapter<FlashcardListAdapter.ViewHolder>() {

    private var data = ArrayList<Flashcard>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.flashcard_edit_card, parent, false)

        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position.toString())
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val idTxt: TextView = view.findViewById(R.id.id_txt)
        fun bind(position: String) {
            idTxt.text = position
        }
    }

    fun setData(newList: ArrayList<Flashcard>) {
        val diffUtil = FlashcardListDiffUtil(data, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        data.clear()
        data.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}