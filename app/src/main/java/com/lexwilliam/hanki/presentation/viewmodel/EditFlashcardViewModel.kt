package com.lexwilliam.hanki.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.lexwilliam.domain.usecase.*
import com.lexwilliam.hanki.model.FlashcardPresentation
import com.lexwilliam.hanki.model.StudySetPresentation
import com.lexwilliam.hanki.presentation.screens.edit_flashcard.EditFlashcardContract
import com.lexwilliam.hanki.presentation.viewmodel.base.BaseViewModel
import com.lexwilliam.hanki.presentation.viewmodel.mapper.FlashcardMapperPresentation
import com.lexwilliam.hanki.presentation.viewmodel.mapper.StudySetMapperPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditFlashcardViewModel @Inject constructor(
    private val getStudySetById: GetStudySetById,
    private val updateStudySet: UpdateStudySet,
    private val insertFlashcard: InsertFlashcard,
    private val updateFlashcard: UpdateFlashcard,
    private val deleteFlashcard: DeleteFlashcard,
    private val studySetMapper: StudySetMapperPresentation,
    private val flashcardMapper: FlashcardMapperPresentation,
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
        return EditFlashcardContract.State(
            isLoading = false,
            isError = false
        )
    }

    override fun handleEvents(event: EditFlashcardContract.Event) {
        when(event) {
            is EditFlashcardContract.Event.AddFlashcard -> {
                insertFlashcard(event.flashcard)
            }
            is EditFlashcardContract.Event.UpdateFlashcard -> {
                updateFlashcard(event.flashcard)
            }
            is EditFlashcardContract.Event.DeleteFlashcard -> {
                deleteFlashcard(event.flashcard)
            }
        }
    }

    private fun getStudySet(id: Long?) {
        viewModelScope.launch(errorHandler) {
            try {
                getStudySetById.execute(id!!)
                    .catch { throwable ->
                        handleExceptions(throwable)
                    }
                    .collect { studySet ->
                        studySetMapper.toPresentation(studySet)
                            .let { studySetPresentation ->
                                setState {
                                    copy(
                                        studySet = studySetPresentation,
                                        isLoading = false
                                    )
                                }

                            }
                    }
            } catch (throwable: Throwable) {
                handleExceptions(throwable)
            }
        }
    }

    private fun insertFlashcard(flashcard: FlashcardPresentation) {
        viewModelScope.launch(errorHandler) {
            try {
                insertFlashcard.execute(flashcardMapper.toDomain(flashcard))
                val studySet = viewState.value.studySet
                updateStudySet.execute(studySetMapper.toDomain(studySet.copy(totalFlashcard = studySet.totalFlashcard + 1)))
            } catch (throwable: Throwable) {
                handleExceptions(throwable)
            }
        }
    }

    private fun updateFlashcard(flashcard: FlashcardPresentation) {
        viewModelScope.launch(errorHandler) {
            try {
                updateFlashcard.execute(flashcardMapper.toDomain(flashcard))
            } catch (throwable: Throwable) {
                handleExceptions(throwable)
            }
        }
    }

    private fun deleteFlashcard(flashcard: FlashcardPresentation) {
        viewModelScope.launch(errorHandler) {
            try {
                deleteFlashcard.execute(flashcardMapper.toDomain(flashcard))
                val studySet = viewState.value.studySet
                updateStudySet.execute(studySetMapper.toDomain(studySet.copy(totalFlashcard = studySet.totalFlashcard - 1)))
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