package com.lexwilliam.feature_add

import androidx.lifecycle.ViewModel
import com.lexwilliam.domain.model.Flashcard
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(

): ViewModel() {

    private fun createFlashcard(flashcard: Flashcard) {

    }
}