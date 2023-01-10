package com.lexwilliam.packs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexwilliam.core.mapper.UserMapper
import com.lexwilliam.core.model.UserPresentation
import com.lexwilliam.domain.model.Result
import com.lexwilliam.domain.usecase.GetUserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PacksViewModel @Inject constructor(
    private val getUserProfile: GetUserProfile,
    private val userMapper: UserMapper
): ViewModel() {

    private var _state = MutableStateFlow(PacksViewState())
    val state = _state.asStateFlow()

    init {
        getUserProfile()
    }

    private fun getUserProfile() {
        viewModelScope.launch {
            getUserProfile.invoke().collect{
                when (it) {
                    is Result.Success -> {
                        _state.value = _state.value
                            .copy(user = Result.Success(userMapper.toPresentation(it.data)))
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
}

data class PacksViewState(
    val user: Result<UserPresentation> = Result.Loading
)