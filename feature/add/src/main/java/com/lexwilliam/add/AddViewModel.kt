package com.lexwilliam.add

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lexwilliam.domain.usecase.GetUserProfile
import com.lexwilliam.core.model.FlashcardPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import com.lexwilliam.domain.model.Result
import com.lexwilliam.domain.usecase.InsertPack
import com.lexwilliam.core.mapper.PackMapper
import com.lexwilliam.core.mapper.UserMapper
import com.lexwilliam.core.model.PackPresentation
import com.lexwilliam.domain.usecase.UploadFile
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val getUserProfile: GetUserProfile,
    private val insertPack: InsertPack,
    private val uploadFile: UploadFile,
    private val packMapper: PackMapper,
    private val userMapper: UserMapper
): ViewModel() {

    private var _packImageUri = MutableStateFlow(Uri.EMPTY)

    fun createPack(
        title: String,
        flashcards: List<FlashcardPresentation>
    ) {
        viewModelScope.launch {
            var packImageUrl = ""
            if (_packImageUri.value != Uri.EMPTY) {
                uploadFile.invoke(UUID.randomUUID().toString(), _packImageUri.value)
                    .collect { result ->
                        when (result) {
                            is Result.Success -> {
                                packImageUrl = result.data
                                Timber.d("Success: $packImageUrl")
                                insertPack(title, packImageUrl, flashcards)
                            }
                            is Result.Loading -> {
                                Timber.d("Loading")
                            }
                            is Result.Error -> {
                                Timber.d("Error: ${result.message}")
                            }
                        }
                    }
            } else {
                insertPack(title, packImageUrl, flashcards)
            }
        }
    }

    private fun insertPack(
        title: String,
        packImageUrl: String,
        flashcards: List<FlashcardPresentation>
    ) {
        viewModelScope.launch {
            getUserProfile.invoke().collect { result ->
                when(result) {
                    is Result.Success -> {
                        val user = userMapper.toPresentation(result.data)
                        val pack = PackPresentation(
                            title = title,
                            pictureUrl = if (packImageUrl == "") null else packImageUrl,
                            creatorName = user.name,
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

    fun setPackImageUri(
        imageUri: Uri
    ) {
        viewModelScope.launch {
            _packImageUri.value = imageUri
        }
    }
}