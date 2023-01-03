package com.lexwilliam.feature_add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexwilliam.domain.usecase.GetUserProfile
import com.lexwilliam.feature_add.model.FlashcardPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import com.lexwilliam.domain.model.Result
import com.lexwilliam.domain.usecase.InsertPack
import com.lexwilliam.feature_add.mapper.PackMapper
import com.lexwilliam.feature_add.model.PackPresentation
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val getUserProfile: GetUserProfile,
    private val insertPack: InsertPack,
    private val packMapper: PackMapper
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
                        insertPack.invoke(packMapper.toDomain(pack))
                        Timber.d(pack.toString())
                    }
                    is Result.Loading -> {
                        Timber.d("Loading")
                    }
                    is Result.Error -> {
                        Timber.d("Error")
                    }
                }
            }
        }
    }
}