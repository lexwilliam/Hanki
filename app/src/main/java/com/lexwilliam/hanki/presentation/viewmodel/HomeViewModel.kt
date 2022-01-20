package com.lexwilliam.hanki.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.lexwilliam.domain.usecase.GetAllStudySets
import com.lexwilliam.domain.usecase.InsertStudySet
import com.lexwilliam.hanki.model.StudySetPresentation
import com.lexwilliam.hanki.presentation.screens.home.HomeContract
import com.lexwilliam.hanki.presentation.viewmodel.base.BaseViewModel
import com.lexwilliam.hanki.presentation.viewmodel.mapper.StudySetMapperPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllStudySets: GetAllStudySets,
    private val insertStudySet: InsertStudySet,
    private val studySetMapper: StudySetMapperPresentation
): BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception)
        setState {
            copy(
                isLoading = false,
                isError = true
            )
        }
    }

    init {
        getStudySets()
    }

    override fun setInitialState(): HomeContract.State {
        return HomeContract.State(
            studySets = emptyList(),
            isLoading = true,
            isError = false
        )
    }

    override fun handleEvents(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.AddStudySet -> {
                insertStudySet(event.studySet)
            }
        }
    }

    private fun getStudySets() {
        viewModelScope.launch(errorHandler) {
            try {
                getAllStudySets.execute()
                    .catch { throwable ->
                        handleExceptions(throwable)
                    }
                    .collect { studySet ->
                        studySetMapper.toPresentation(studySet)
                            .let { studySetPresentation ->
                                setState {
                                    copy(
                                        studySets = studySetPresentation,
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

    private fun insertStudySet(
        studySet: StudySetPresentation
    ) {
        viewModelScope.launch(errorHandler) {
            try {
                insertStudySet.execute(studySetMapper.toDomain(studySet))
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