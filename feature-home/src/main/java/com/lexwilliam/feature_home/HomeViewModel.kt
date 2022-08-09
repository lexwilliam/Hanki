package com.lexwilliam.feature_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexwilliam.domain.usecase.InsertTest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val insertTest: InsertTest
): ViewModel() {

    fun insertTest() {
        viewModelScope.launch {
            insertTest.invoke()
        }
    }

}