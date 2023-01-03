package com.lexwilliam.feature_packs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexwilliam.domain.usecase.GetPack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PacksViewModel @Inject constructor(
    private val getPack: GetPack
): ViewModel() {

    fun getPack(id: String) {
        viewModelScope.launch {
            getPack.invoke(id).collect {

            }
        }
    }
}