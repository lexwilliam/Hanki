package com.lexwilliam.hanki

import androidx.lifecycle.ViewModel
import com.lexwilliam.domain.usecase.IsUserAuthenticated
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isUserAuthenticated: IsUserAuthenticated
): ViewModel() {
    private val _isAuthenticated: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    init {
        Timber.d(isUserAuthenticated.invoke().toString())
        _isAuthenticated.value = isUserAuthenticated.invoke()
    }


}