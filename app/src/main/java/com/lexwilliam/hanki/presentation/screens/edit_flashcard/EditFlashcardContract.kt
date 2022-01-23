package com.lexwilliam.hanki.presentation.screens.edit_flashcard

import com.lexwilliam.hanki.model.FlashcardPresentation
import com.lexwilliam.hanki.model.StudySetPresentation
import com.lexwilliam.hanki.presentation.viewmodel.base.ViewEvent
import com.lexwilliam.hanki.presentation.viewmodel.base.ViewSideEffect
import com.lexwilliam.hanki.presentation.viewmodel.base.ViewState

class EditFlashcardContract {
    sealed class Event: ViewEvent {
        data class AddFlashcard(val flashcard: FlashcardPresentation): Event()
        data class UpdateFlashcard(val flashcard: FlashcardPresentation): Event()
        data class DeleteFlashcard(val flashcard: FlashcardPresentation): Event()
        data class AddCountStudySet(val studySet: StudySetPresentation): Event()
        data class SubtractCountStudySet(val studySet: StudySetPresentation): Event()
    }

    data class State(
        val studySet: StudySetPresentation = StudySetPresentation(-1, "", -1, emptyList()),
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ): ViewState

    object Effect: ViewSideEffect
}