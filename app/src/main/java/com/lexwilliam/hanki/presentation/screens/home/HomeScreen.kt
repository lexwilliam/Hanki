package com.lexwilliam.hanki.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lexwilliam.hanki.model.StudySetPresentation
import com.lexwilliam.hanki.presentation.common.StudySetGridView
import com.lexwilliam.hanki.presentation.common.fakeStudySet
import com.lexwilliam.hanki.presentation.common.fakeStudySetList
import com.lexwilliam.hanki.ui.theme.HankiTheme
import com.lexwilliam.hanki.util.TimeProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    state: HomeContract.State,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    coroutineScope: CoroutineScope
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HomeToolBar()
        StudySetHorizontalList(studySets = state.studySets)
        AddStudySetButton(
            onClick = {
                coroutineScope.launch {
                    if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    } else {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            }
        )
        Spacer(modifier = Modifier.padding(24.dp))
    }
}

@Composable
fun HomeToolBar() {
    Row(Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)) {
        Box(Modifier.weight(1f) ,contentAlignment = Alignment.CenterStart) {
            Column {
                Text(text = TimeProvider.getCurrentDate(), style = MaterialTheme.typography.caption)
                Text(text = "Hi, Alex", style = MaterialTheme.typography.h6)
            }
        }
        Box(Modifier.weight(1f) ,contentAlignment = Alignment.CenterEnd) {
            Box(
                Modifier
                    .size(36.dp)
                    .shadow(8.dp, MaterialTheme.shapes.small)
                    .background(MaterialTheme.colors.primary))
        }
    }
}

@Composable
fun StudySetHorizontalList(
    studySets: List<StudySetPresentation>
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        ) {
            Box(Modifier.weight(1f) ,contentAlignment = Alignment.CenterStart) {
                Text("Your Sets", style = MaterialTheme.typography.subtitle2)
            }
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                Text("See All", style = MaterialTheme.typography.caption)
            }
        }
        Row(Modifier.padding(start = 16.dp)) {
            LazyRow {
                items(studySets) { studySet ->
                    StudySetGridView(modifier = Modifier.padding(end = 16.dp), studySet = studySet)
                }
            }
        }
    }
}

@Composable
fun AddStudySetButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onClick = { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.AddCircle, contentDescription = null)
            Spacer(modifier = Modifier.padding(16.dp))
            Text(text = "Add a Study Set", style = MaterialTheme.typography.button)
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HankiTheme {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colors.background)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HomeToolBar()
            StudySetHorizontalList(studySets = fakeStudySetList)
            AddStudySetButton(onClick = {})
            Spacer(modifier = Modifier.padding(24.dp))
        }
    }
}