package com.lexwilliam.feature_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexwilliam.core.mapper.PackMapper
import com.lexwilliam.core.model.PackInfoPresentation
import com.lexwilliam.domain.model.Result
import com.lexwilliam.domain.model.Test
import com.lexwilliam.domain.model.User
import com.lexwilliam.domain.usecase.GetUserPackList
import com.lexwilliam.domain.usecase.GetUserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserPackList: GetUserPackList,
    private val getUserProfile: GetUserProfile,
    private val packMapper: PackMapper
): ViewModel() {

    private var _state = MutableStateFlow(HomeViewState())
    val state = _state.asStateFlow()

    init {
        getUserProfile()
    }

    private fun getUserProfile() {
        viewModelScope.launch {
            getUserProfile.invoke().collect {
                when (it) {
                    is Result.Success -> {
                        getUserPackList(it.data.uid)
                        _state.value = _state.value.copy(user = Result.Success(it.data))
                    }
                    is Result.Loading -> {
                        _state.value = _state.value.copy(user = Result.Loading)
                    }
                    is Result.Error -> {
                        _state.value = _state.value.copy(user = Result.Error(it.message))
                    }
                }
            }
        }
    }

    fun getUserPackList(userId: String) {
        viewModelScope.launch {
            getUserPackList.invoke(userId).collect { result ->
                when (result) {
                    is Result.Success -> {
                        val packs = result.data.map { packMapper.toPresentation(it) }
                        _state.value = _state.value.copy(userPackList = Result.Success(packs))
                        Timber.d("Success")
                        Timber.d(packs.toString())
                    }
                    is Result.Loading -> {
                        _state.value = _state.value.copy(userPackList = Result.Loading)
                        Timber.d("Loading")
                    }
                    is Result.Error -> {
                        _state.value = _state.value.copy(userPackList = Result.Error(result.message))
                        Timber.d("Error")
                    }
                }
            }
        }
    }
}

data class HomeViewState(
    val tests: Result<List<Test>> = Result.Loading,
    val user: Result<User> = Result.Loading,
    val userPackList: Result<List<PackInfoPresentation>> = Result.Loading
)