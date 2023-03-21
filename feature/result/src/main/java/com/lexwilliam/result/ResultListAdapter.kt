package com.lexwilliam.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lexwilliam.core.model.FlashcardPresentation
import com.lexwilliam.core.model.TestResultPresentation
import com.lexwilliam.result.databinding.TestResultCardBinding
import timber.log.Timber

class ResultListAdapter(
    private val wordList: List<TestResultPresentation.Word>
): RecyclerView.Adapter<ResultListAdapter.TestResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestResultViewHolder =
        TestResultViewHolder(
            TestResultCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TestResultViewHolder, position: Int) {
        holder.bind(wordList[position])
    }

    override fun getItemCount(): Int {
        return wordList.size
    }


    inner class TestResultViewHolder(private val binding: TestResultCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(word: TestResultPresentation.Word) {
            binding.word = word
        }
    }
}