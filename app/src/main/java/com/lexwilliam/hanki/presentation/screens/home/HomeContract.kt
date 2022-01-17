package com.lexwilliam.hanki.presentation.screens.home

import com.lexwilliam.hanki.model.StudySetPresentation
import com.lexwilliam.hanki.presentation.viewmodel.base.ViewEvent
import com.lexwilliam.hanki.presentation.viewmodel.base.ViewSideEffect
import com.lexwilliam.hanki.presentation.viewmodel.base.ViewState

class HomeContract {
    sealed class Event: ViewEvent

    data class State(
        val studySetPresentation: List<StudySetPresentation>,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ): ViewState

    object Effect: ViewSideEffect
}
