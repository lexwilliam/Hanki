package com.lexwilliam.hanki.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lexwilliam.hanki.model.FlashcardPresentation
import com.lexwilliam.hanki.model.StudySetPresentation
import com.lexwilliam.hanki.ui.theme.HankiTheme
import com.lexwilliam.hanki.ui.theme.NotoSans
import com.lexwilliam.hanki.ui.theme.NotoSansTypography

@Composable
fun StudySetGridView(
    modifier: Modifier = Modifier,
    studySet: StudySetPresentation
) {
    Column(
        modifier
            .height(246.dp)
            .width(178.dp)
            .shadow(8.dp, MaterialTheme.shapes.large, true)
            .background(MaterialTheme.colors.background)
    ) {
        Box(
            Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            Box(
                Modifier
                    .height(160.dp)
                    .width(144.dp)
                    .clip(RoundedCornerShape(0.dp, 16.dp, 0.dp, 16.dp))
                    .background(MaterialTheme.colors.primary)
            )
        }
        Column(Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
            Text(text = studySet.name, style = MaterialTheme.typography.h6)
            Text(text = studySet.totalFlashcard.toString() + " cards", style = MaterialTheme.typography.caption)
        }
    }
}

@Preview
@Composable
fun StudySetGridViewPreview() {
    HankiTheme {
        StudySetGridView(studySet = fakeStudySet)
    }
}

val fakeFlashcardList = listOf(
    FlashcardPresentation(1, "Fake Study Set", "Fake Question 1", "Fake Answer 1"),
    FlashcardPresentation(2, "Fake Study Set", "Fake Question 2", "Fake Answer 2"),
    FlashcardPresentation(3, "Fake Study Set", "Fake Question 3", "Fake Answer 3"),
    FlashcardPresentation(4, "Fake Study Set", "Fake Question 4", "Fake Answer 4"),
    FlashcardPresentation(5, "Fake Study Set", "Fake Question 5", "Fake Answer 5")
)

val fakeStudySet = StudySetPresentation(0, "Fake Study Set", 5, fakeFlashcardList)

val fakeStudySetList = listOf(fakeStudySet, fakeStudySet, fakeStudySet, fakeStudySet)