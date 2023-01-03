package com.lexwilliam.feature_add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexwilliam.domain.usecase.GetUserProfile
import com.lexwilliam.feature_add.model.FlashcardPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import com.lexwilliam.domain.model.Result
import com.lexwilliam.feature_add.model.PackPresentation
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val getUserProfile: GetUserProfile
): ViewModel() {



    fun createPack(
        name: String,
        flashcards: List<FlashcardPresentation>
    ) {
        viewModelScope.launch {
            getUserProfile.invoke().collect { result ->
                when(result) {
                    is Result.Success -> {
                        val pack = PackPresentation(
                            name = name,
                            creatorName = result.data.name?:"",
                            creatorPhotoUrl = result.data.photoUrl,
                            flashcards = flashcards
                        )
                        Timber.d(pack.toString())
                    }
                    is Result.Loading -> {
                        Timber.d("Loading")
                    }
                    else -> {
                        Timber.d("Error")
                    }
                }
            }
        }

    }
}