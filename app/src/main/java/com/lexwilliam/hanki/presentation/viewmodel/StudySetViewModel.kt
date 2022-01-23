package com.lexwilliam.hanki.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.lexwilliam.domain.usecase.GetStudySetById
import com.lexwilliam.domain.usecase.InsertFlashcard
import com.lexwilliam.domain.usecase.UpdateFlashcard
import com.lexwilliam.hanki.model.FlashcardPresentation
import com.lexwilliam.hanki.model.StudySetPresentation
import com.lexwilliam.hanki.presentation.screens.study_set.StudySetContract
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
class StudySetViewModel @Inject constructor(
    private val getStudySetById: GetStudySetById,
    private val insertFlashcard: InsertFlashcard,
    private val updateFlashcard: UpdateFlashcard,
    private val studySetMapper: StudySetMapperPresentation,
    private val flashcardMapper: FlashcardMapperPresentation,
    savedStateHandle: SavedStateHandle
): BaseViewModel<StudySetContract.Event, StudySetContract.State, StudySetContract.Effect>() {

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

    override fun setInitialState(): StudySetContract.State {
        return StudySetContract.State(
            studySet = StudySetPresentation(-1, "", -1, emptyList()),
            isLoading = true,
            isError = false
        )
    }

    override fun handleEvents(event: StudySetContract.Event) {
        when(event) {
            is StudySetContract.Event.InsertFlashcard -> {
                insertFlashcard(event.flashcard)
            }
            is StudySetContract.Event.UpdateFlashcard -> {
                updateFlashcard(event.flashcard)
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