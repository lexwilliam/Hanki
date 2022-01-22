package com.lexwilliam.hanki.presentation.screens.edit_flashcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lexwilliam.domain.model.Flashcard
import com.lexwilliam.hanki.model.FlashcardPresentation
import com.lexwilliam.hanki.presentation.common.fakeFlashcardList

@ExperimentalComposeUiApi
@Composable
fun EditFlashcardScreen(
    onBackStackPressed: () -> Unit,
    state: EditFlashcardContract.State,
    onEventSent: (EditFlashcardContract.Event) -> Unit
) {
    Column {
        EditableFlashcardList(
            onEventSent = { onEventSent(it) },
            flashcards = state.studySet.flashcards
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun EditableFlashcardList(
    onEventSent: (EditFlashcardContract.Event) -> Unit,
    flashcards: List<FlashcardPresentation>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(flashcards) { flashcard ->
            EditableFlashcardCardView(
                onEventSent = { onEventSent(it) },
                flashcard = flashcard
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun EditableFlashcardCardView(
    modifier: Modifier = Modifier,
    onEventSent: (EditFlashcardContract.Event) -> Unit,
    flashcard: FlashcardPresentation
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(8.dp, MaterialTheme.shapes.medium, true)
            .background(MaterialTheme.colors.background)
    ) {
        Column(
            Modifier.padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = question,
                onValueChange = { question = it },
                label = { Text("Question") },
                keyboardActions = KeyboardActions(onAny = {
                    onEventSent(
                        EditFlashcardContract.Event.UpdateFlashcard(
                            FlashcardPresentation(
                                id = flashcard.id,
                                studySetName = flashcard.studySetName,
                                question = question,
                                answer = flashcard.answer
                            )
                        )
                    )
                    keyboardController?.hide()
                })
            )
            Spacer(modifier = Modifier.padding(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = answer,
                onValueChange = { answer = it },
                label = { Text("Answer") },
                keyboardActions = KeyboardActions(onAny = {
                    onEventSent(
                        EditFlashcardContract.Event.UpdateFlashcard(
                            FlashcardPresentation(
                                id = flashcard.id,
                                studySetName = flashcard.studySetName,
                                question = flashcard.question,
                                answer = answer
                            )
                        )
                    )
                    keyboardController?.hide()
                })
            )
        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun EditableFlashcardListPreview() {
    EditableFlashcardList(onEventSent = {}, flashcards = fakeFlashcardList)
}