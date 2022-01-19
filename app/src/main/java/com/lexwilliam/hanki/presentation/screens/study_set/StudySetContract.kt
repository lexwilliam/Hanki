package com.lexwilliam.hanki.presentation.screens.study_set

import com.lexwilliam.hanki.model.StudySetPresentation
import com.lexwilliam.hanki.presentation.viewmodel.base.ViewEvent
import com.lexwilliam.hanki.presentation.viewmodel.base.ViewSideEffect
import com.lexwilliam.hanki.presentation.viewmodel.base.ViewState

class StudySetContract {
    sealed class Event: ViewEvent

    data class State(
        val studySet: StudySetPresentation,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ): ViewState

    object Effect: ViewSideEffect
}