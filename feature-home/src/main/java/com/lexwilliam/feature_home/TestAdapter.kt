package com.lexwilliam.feature_home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lexwilliam.domain.model.Test

class TestAdapter(private val tests: List<Test>):
    RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val first: TextView = view.findViewById(R.id.first_txt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_test, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.first.text = tests[position].first
    }

    override fun getItemCount(): Int {
        return tests.size
    }
}