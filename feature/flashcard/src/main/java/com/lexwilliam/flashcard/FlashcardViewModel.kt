package com.lexwilliam.flashcard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexwilliam.core.mapper.PackMapper
import com.lexwilliam.core.mapper.TestResultMapper
import com.lexwilliam.core.model.PackPresentation
import com.lexwilliam.core.model.TestResultPresentation
import com.lexwilliam.domain.model.Result
import com.lexwilliam.domain.usecase.GetPack
import com.lexwilliam.domain.usecase.GetTestResult
import com.lexwilliam.domain.usecase.InsertTestResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlashcardViewModel @Inject constructor(
    private val getPack: GetPack,
    private val insertTestResult: InsertTestResult,
    private val testResultMapper: TestResultMapper,
    private val packMapper: PackMapper
): ViewModel() {

    private var _state = MutableStateFlow(FlashcardViewState())
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

    fun insertTestResult(testResult: TestResultPresentation) {
        viewModelScope.launch {
            insertTestResult.invoke(testResultMapper.toDomain(testResult))
        }
    }
}

data class FlashcardViewState(
    val pack: Result<PackPresentation> = Result.Loading
)