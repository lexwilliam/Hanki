package com.lexwilliam.feature_auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexwilliam.domain.usecase.InsertUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val insertUser: InsertUser
): ViewModel() {

    fun insertUser() {
        viewModelScope.launch {
            insertUser.invoke()
        }
    }
}