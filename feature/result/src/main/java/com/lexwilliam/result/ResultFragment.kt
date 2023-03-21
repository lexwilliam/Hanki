package com.lexwilliam.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.lexwilliam.core.model.TestResultPresentation
import com.lexwilliam.core.util.ItemOffsetDecoration
import com.lexwilliam.result.databinding.FragmentResultBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.lexwilliam.domain.model.Result
import timber.log.Timber

@AndroidEntryPoint
class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
    private val viewModel: ResultViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)

        val argument = arguments?.getString("packId")

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (val testResult = state.testResult) {
                    is Result.Success -> {
                        binding.rvTestResultList.apply {
                            argument?.let { packId ->
                                val pack = filterByPack(packId, testResult.data)
                                adapter = ResultListAdapter(pack)
                                layoutManager = LinearLayoutManager(requireContext())
                                addItemDecoration(ItemOffsetDecoration(32, false))
                            }
                        }
                    }
                    is Result.Loading -> Timber.d("Loading")
                    is Result.Error -> Timber.e(testResult.message)
                }
            }
        }


        return binding.root
    }

    fun filterByPack(packId: String, testResult: TestResultPresentation) =
        testResult.wordList.filter { word -> word.packIdList.contains(packId) }
}