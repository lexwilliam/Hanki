package com.lexwilliam.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexwilliam.core.mapper.PackMapper
import com.lexwilliam.core.model.PackPresentation
import com.lexwilliam.domain.usecase.GetPack
import dagger.hilt.android.lifecycle.HiltViewModel
import com.lexwilliam.domain.model.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPack: GetPack,
    private val packMapper: PackMapper
): ViewModel() {

    private var _state = MutableStateFlow(DetailViewState())
    val state = _state.asStateFlow()

    fun getPack(id: String) {
        viewModelScope.launch {
            getPack.invoke(id).collect {
                when (it) {
                    is Result.Success -> {
                        val data = packMapper.toPresentation(it.data)
                        _state.value = _state.value.copy(pack = Result.Success(data))
                    }
                    is Result.Loading -> {
                        _state.value = _state.value.copy(pack = Result.Loading)
                    }
                    is Result.Error -> {
                        _state.value = _state.value.copy(pack = Result.Error(it.message))
                    }
                }
            }
        }
    }

}

data class DetailViewState(
    val pack: Result<PackPresentation> = Result.Loading
)