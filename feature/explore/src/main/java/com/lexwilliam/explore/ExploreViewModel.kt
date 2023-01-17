package com.lexwilliam.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexwilliam.core.mapper.PackMapper
import com.lexwilliam.core.model.PackPresentation
import com.lexwilliam.domain.usecase.GetPackCollection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.lexwilliam.domain.model.Result
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getPackCollection: GetPackCollection,
    private val packMapper: PackMapper
): ViewModel() {

    private var _state = MutableStateFlow(ExploreViewState())
    val state = _state.asStateFlow()

    init {
        getPackCollection()
    }

    private fun getPackCollection() {
        viewModelScope.launch {
            getPackCollection.invoke().collect { result ->
                when (result) {
                    is Result.Success -> {
                        _state.value = _state.value.copy(
                            packs = Result.Success(result.data.map { packMapper.toPresentation(it) })
                        )
                    }
                    is Result.Loading -> {
                        _state.value = _state.value.copy(
                            packs = Result.Loading
                        )
                    }
                    is Result.Error -> {
                        _state.value = _state.value.copy(
                            packs = Result.Error(result.message)
                        )
                    }
                }
            }
        }
    }
}

data class ExploreViewState(
    val packs: Result<List<PackPresentation>> = Result.Loading
)