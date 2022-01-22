package com.lexwilliam.hanki.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.lexwilliam.domain.usecase.GetStudySetById
import com.lexwilliam.hanki.presentation.screens.edit_flashcard.EditFlashcardContract
import com.lexwilliam.hanki.presentation.viewmodel.base.BaseViewModel
import com.lexwilliam.hanki.presentation.viewmodel.mapper.StudySetMapperPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditFlashcardViewModel @Inject constructor(
    private val getStudySetById: GetStudySetById,
    private val studySetMapper: StudySetMapperPresentation,
    savedStateHandle: SavedStateHandle
): BaseViewModel<EditFlashcardContract.Event, EditFlashcardContract.State, EditFlashcardContract.Effect>() {

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception)
        setState {
            copy(
                isLoading = false,
                isError = true
            )
        }
    }

    private val studySetIdFromArgs = savedStateHandle.get<Long>("studySet_id")

    init {
        getStudySet(studySetIdFromArgs)
    }

    override fun setInitialState(): EditFlashcardContract.State {
        TODO("Not yet implemented")
    }

    override fun handleEvents(event: EditFlashcardContract.Event) {
        TODO("Not yet implemented")
    }

    private fun getStudySet(id: Long?) {
        viewModelScope.launch(errorHandler) {
            try {
                id?.let { studySetId ->
                    getStudySetById.execute(studySetId)?.let { studySet ->
                        setState {
                            copy(
                                studySet = studySetMapper.toPresentation(studySet)
                            )
                        }
                    }
                }
            } catch (throwable: Throwable) {
                handleExceptions(throwable)
            }
        }
    }

    private fun handleExceptions(throwable: Throwable) {
        Timber.e(throwable)
        setState {
            copy(
                isLoading = false,
                isError = true
            )
        }
    }


}