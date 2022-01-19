package com.lexwilliam.hanki.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lexwilliam.hanki.model.StudySetPresentation
import com.lexwilliam.hanki.presentation.common.StudySetGridView
import com.lexwilliam.hanki.presentation.common.fakeStudySet
import com.lexwilliam.hanki.presentation.common.fakeStudySetList

@Composable
fun HomeScreen(
    state: HomeContract.State
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HomeToolBar()
        StudySetHorizontalListTitle()
        StudySetHorizontalList(studySets = state.studySets)
    }
}

@Composable
fun HomeToolBar() {
    Text(text = "Hi, Alex", style = MaterialTheme.typography.h6)
}

@Composable
fun StudySetHorizontalListTitle() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
    ) {
        Box(Modifier.weight(1f) ,contentAlignment = Alignment.CenterStart) {
            Text("Your Sets", style = MaterialTheme.typography.subtitle1)
        }
        Box(Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
            Text("See All", style = MaterialTheme.typography.subtitle1)
        }
    }
}

@Composable
fun StudySetHorizontalList(
    studySets: List<StudySetPresentation>
) {
    Row(Modifier.padding(start = 16.dp)) {
        LazyRow {
            items(studySets) { studySet ->
                StudySetGridView(modifier = Modifier.padding(end = 16.dp), studySet = studySet)
            }
        }
    }
}

@Preview
@Composable
fun StudySetHorizontalListPreview() {
    Column {
        StudySetHorizontalListTitle()
        StudySetHorizontalList(studySets = fakeStudySetList)
    }
}