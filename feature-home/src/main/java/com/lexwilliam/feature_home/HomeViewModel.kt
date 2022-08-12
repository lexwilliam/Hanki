package com.lexwilliam.feature_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexwilliam.domain.model.Result
import com.lexwilliam.domain.model.Test
import com.lexwilliam.domain.usecase.InsertTest
import com.lexwilliam.domain.usecase.ReadTest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val insertTest: InsertTest,
    private val readTest: ReadTest
): ViewModel() {

    private var _state = MutableStateFlow(HomeViewState())
    val state = _state.asStateFlow()

    init {
        readTest()
    }

    fun insertTest() {
        viewModelScope.launch {
            insertTest.invoke()
        }
    }

    private fun readTest() {
        viewModelScope.launch {
            readTest.invoke().collect { tests ->
                _state.value = _state.value.copy(tests = tests)
                Timber.d(_state.value.tests.toString())
            }
        }
    }

}

data class HomeViewState(
    val tests: Result<List<Test>> = Result.Loading
)