package com.lexwilliam.hanki.presentation.screens.study_set

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lexwilliam.hanki.model.FlashcardPresentation
import com.lexwilliam.hanki.presentation.common.fakeFlashcardList
import com.lexwilliam.hanki.presentation.common.fakeStudySet
import com.lexwilliam.hanki.ui.theme.HankiTheme

@Composable
fun StudySetScreen(
    state: StudySetContract.State,
    onBackStackPressed: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StudySetToolBar(studySetName = state.studySet.name, onBackStackPressed = { onBackStackPressed() })
        FlashcardVerticalList(totalFlashcard = state.studySet.totalFlashcard, flashcards = state.studySet.flashcards)
    }
}

@Composable
fun StudySetToolBar(
    studySetName: String,
    onBackStackPressed: () -> Unit
) {
    Row(
        Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .height(IntrinsicSize.Min)
    ) {
        Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
            Row(Modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier
                        .clickable { onBackStackPressed() },
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
                Text(modifier = Modifier.padding(start = 16.dp), text = studySetName, style = MaterialTheme.typography.h6)
            }
        }
        Box(Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
            Box(
                Modifier
                    .size(36.dp)
                    .shadow(8.dp, MaterialTheme.shapes.small, true)
                    .background(MaterialTheme.colors.primary)
            )
        }
    }
}

@Composable
fun FlashcardVerticalList(
    totalFlashcard: Int,
    flashcards: List<FlashcardPresentation>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
            ) {
                Box(Modifier.weight(1f) ,contentAlignment = Alignment.CenterStart) {
                    Text( "$totalFlashcard Cards", style = MaterialTheme.typography.subtitle1)
                }
                Box(Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                    Text("Filter", style = MaterialTheme.typography.subtitle1)
                }
            }
        }
        items(flashcards) { flashcard ->
            FlashcardColumnView(modifier = Modifier.padding(horizontal = 16.dp), flashcard = flashcard)
        }
        item {
            Spacer(modifier = Modifier.padding(24.dp))
        }
    }
}

@Composable
fun FlashcardColumnView(
    modifier: Modifier = Modifier,
    flashcard: FlashcardPresentation
) {
    Box(
        modifier
            .fillMaxWidth()
            .shadow(8.dp, MaterialTheme.shapes.medium, true)
            .background(MaterialTheme.colors.background),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = flashcard.question, style = MaterialTheme.typography.h6)
                    Text(text = flashcard.answer, style = MaterialTheme.typography.subtitle1)
                }
            }
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                Icon(Icons.Default.Star, contentDescription = null)
            }
        }
    }
}

@Preview
@Composable
fun StudySetScreenPreview() {
    HankiTheme {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StudySetToolBar(studySetName = fakeStudySet.name, onBackStackPressed = {})
            FlashcardVerticalList(totalFlashcard = 5, flashcards = fakeFlashcardList)
        }
    }
}