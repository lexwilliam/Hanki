package com.lexwilliam.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexwilliam.core.mapper.TestResultMapper
import com.lexwilliam.core.model.TestResultPresentation
import com.lexwilliam.domain.usecase.GetTestResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.lexwilliam.domain.model.Result
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val getTestResult: GetTestResult,
    private val testResultMapper: TestResultMapper
): ViewModel() {

    private var _state = MutableStateFlow(ResultViewState())
    val state = _state.asStateFlow()

    init {
        getTestResult()
    }

    private fun getTestResult() {
        viewModelScope.launch {
            getTestResult.invoke().collect {
                when (it) {
                    is Result.Success -> {
                        val data = testResultMapper.toPresentation(it.data)
                        _state.value = _state.value.copy(testResult = Result.Success(data))
                    }
                    is Result.Loading -> {
                        _state.value = _state.value.copy(testResult = Result.Loading)
                    }
                    is Result.Error -> {
                        _state.value = _state.value.copy(testResult = Result.Error(it.message))
                    }
                }
            }
        }
    }

}

data class ResultViewState(
    val testResult: Result<TestResultPresentation> = Result.Loading
)