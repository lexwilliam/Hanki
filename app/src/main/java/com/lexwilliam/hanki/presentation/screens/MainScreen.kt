package com.lexwilliam.hanki.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lexwilliam.hanki.model.FlashcardPresentation
import com.lexwilliam.hanki.model.StudySetPresentation
import com.lexwilliam.hanki.presentation.navigation.BottomNavigationScreens
import com.lexwilliam.hanki.presentation.screens.edit_flashcard.EditFlashcardContract
import com.lexwilliam.hanki.presentation.screens.edit_flashcard.EditFlashcardScreen
import com.lexwilliam.hanki.presentation.screens.home.HomeContract
import com.lexwilliam.hanki.presentation.screens.home.HomeScreen
import com.lexwilliam.hanki.presentation.screens.study_set.StudySetScreen
import com.lexwilliam.hanki.presentation.viewmodel.EditFlashcardViewModel
import com.lexwilliam.hanki.presentation.viewmodel.HomeViewModel
import com.lexwilliam.hanki.presentation.viewmodel.StudySetViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun MainScreen() {
    val navController = rememberNavController()

//    val systemUiController = rememberSystemUiController()
//    systemUiController.setStatusBarColor(
//    if (MaterialTheme.colors.isLight) Light.StatusBar
//    else Dark.StatusBar
//    )

    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Home
    )

    Scaffold(
        bottomBar = {
            HankiBottomNavigation(
                navController = navController,
                items = bottomNavigationItems
            )
        }
    ) { innerPadding ->
        MainScreenNavigationConfigurations(navController, innerPadding)
    }

}

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
private fun MainScreenNavigationConfigurations(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(navController = navController, startDestination = "home") {
        composable(Screens.HomeScreen.route) {
            InitHomeScreen(
                paddingValues = paddingValues,
                navToStudySet = { studySet_id ->
                    navController.navigate(
                        Screens.StudySetScreen.route
                            .plus("/?studySet_id=$studySet_id")
                    )
                }
            )
        }
        composable(
            route = Screens.StudySetScreen.route.plus("/?studySet_id={studySet_id}"),
            arguments = listOf(
                    navArgument("studySet_id") {
                    type = NavType.LongType
                    defaultValue = -1
                }
            )
        ) {
            InitStudySetScreen(
                onBackStackPressed = {
                    navController.navigateUp()
                },
                navToEditFlashcard = {
                    navController.navigate(Screens.EditFlashcardScreen.route)
                }
            )
        }
        composable(
            route = Screens.EditFlashcardScreen.route.plus("/?studySet_id={studySet_id}"),
            arguments = listOf(
                navArgument("studySet_id") {
                    type = NavType.LongType
                    defaultValue = -1
                }
            )
        ) {
            InitEditFlashcard(
                onBackStackPressed = {
                    navController.navigateUp()
                },
                paddingValues = paddingValues
            )
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
private fun InitHomeScreen(
    paddingValues: PaddingValues,
    navToStudySet: (Long) -> Unit
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    HomeBottomSheet(
        bottomSheetScaffoldState = bottomSheetScaffoldState,
        coroutineScope = coroutineScope,
        homeViewModel = homeViewModel,
        paddingValues = paddingValues,
        navToStudySet = { navToStudySet(it) }
    )
}

@Composable
private fun InitStudySetScreen(
    onBackStackPressed: () -> Unit,
    navToEditFlashcard: () -> Unit
) {
    val studySetViewModel: StudySetViewModel = hiltViewModel()
    StudySetFloatingActionBtn(
        studySetViewModel = studySetViewModel,
        onBackStackPressed = { onBackStackPressed() },
        onFabClick = { navToEditFlashcard() }
    )
}

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
private fun InitEditFlashcard(
    onBackStackPressed: () -> Unit,
    paddingValues: PaddingValues
) {
    val editFlashcardViewModel: EditFlashcardViewModel = hiltViewModel()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    EditFlashcardBottomSheet(
        bottomSheetScaffoldState = bottomSheetScaffoldState,
        coroutineScope = coroutineScope,
        editFlashcardViewModel = editFlashcardViewModel,
        onBackStackPressed = onBackStackPressed,
        paddingValues = paddingValues
    )
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun HankiBottomNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreens>
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.secondary,
        elevation = 16.dp
    ) {
        val currentRoute = currentRoute(navController = navController)

        items.forEach { screen ->
            BottomTab(screen, currentRoute, navController)
        }
    }
}

@Composable
private fun RowScope.BottomTab(
    screen: BottomNavigationScreens,
    currentRoute: String?,
    navController: NavHostController
) {
    val onClick: () -> Unit = {
        if (currentRoute != screen.route) {
            navController.navigate(screen.route)
        }
    }
    BottomNavigationItem(
        icon = {
            Icon (
                painter = painterResource(id = screen.drawableRes),
                contentDescription = null
            )
        },
        label = { Text(screen.description) },
        selected = currentRoute == screen.route,
        onClick = onClick
    )
}

@Composable
fun StudySetFloatingActionBtn(
    studySetViewModel: StudySetViewModel,
    onBackStackPressed: () -> Unit,
    onFabClick: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(bottom = 54.dp),
                onClick = { onFabClick() }
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        StudySetScreen(
            state = studySetViewModel.viewState.value,
            onBackStackPressed = { onBackStackPressed() }
        )
    }
}

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
private fun HomeBottomSheet(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    coroutineScope: CoroutineScope,
    homeViewModel: HomeViewModel,
    paddingValues: PaddingValues,
    navToStudySet: (Long) -> Unit
) {
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            HomeBottomSheetContent(
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                coroutineScope = coroutineScope,
                onEventSent = { event -> homeViewModel.setEvent(event)}
            )
        },
        sheetPeekHeight = 0.dp,
        sheetElevation = 8.dp,
        sheetShape = RoundedCornerShape(
            topStart = 12.dp,
            topEnd = 12.dp
        ),
        modifier = Modifier
            .padding(paddingValues)
            .wrapContentHeight()
    ) {
        HomeScreen(
            state = homeViewModel.viewState.value,
            bottomSheetScaffoldState = bottomSheetScaffoldState,
            coroutineScope = coroutineScope,
            navToStudySet = { navToStudySet(it) }
        )
    }
}

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun HomeBottomSheetContent(
    modifier: Modifier = Modifier,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    coroutineScope: CoroutineScope,
    onEventSent: (event: HomeContract.Event) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var setName by remember { mutableStateOf("") }
    Column(
        modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Add a Study Set", style = MaterialTheme.typography.h5)
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Set name")},
                value = setName,
                onValueChange = { setName = it },
                keyboardActions = KeyboardActions(onAny = {
                    keyboardController?.hide()
                })
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary),
                onClick = {
                    onEventSent(
                        HomeContract.Event.AddStudySet(
                            StudySetPresentation(
                                name = setName,
                                totalFlashcard = 0,
                                flashcards = emptyList()
                            )
                        )
                    )
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        } else {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    }
                }
            ) {
                Text(text = "Confirm", style = MaterialTheme.typography.button)
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun EditFlashcardBottomSheet(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    coroutineScope: CoroutineScope,
    editFlashcardViewModel: EditFlashcardViewModel,
    paddingValues: PaddingValues,
    onBackStackPressed: () -> Unit
) {
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            EditFlashcardBottomSheetContent(
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                coroutineScope = coroutineScope,
                studySetName = editFlashcardViewModel.viewState.value.studySet.name,
                onEventSent = { event -> editFlashcardViewModel.setEvent(event)}
            )
        },
        sheetPeekHeight = 0.dp,
        sheetElevation = 8.dp,
        sheetShape = RoundedCornerShape(
            topStart = 12.dp,
            topEnd = 12.dp
        ),
        modifier = Modifier
            .padding(paddingValues)
            .wrapContentHeight()
    ) {
        EditFlashcardScreen(
            onBackStackPressed = {
                onBackStackPressed()
            },
            state = editFlashcardViewModel.viewState.value,
            onEventSent = { event -> editFlashcardViewModel.setEvent(event)}
        )
    }
}

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun EditFlashcardBottomSheetContent(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    coroutineScope: CoroutineScope,
    studySetName: String,
    onEventSent: (EditFlashcardContract.Event) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, MaterialTheme.shapes.medium, true)
            .background(MaterialTheme.colors.background)
    ) {
        Column(
            Modifier.padding(16.dp)
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = question,
                onValueChange = { question = it },
                label = { Text("Question") },
                keyboardActions = KeyboardActions(onAny = { keyboardController?.hide() })
            )
            Spacer(modifier = Modifier.padding(16.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = answer,
                onValueChange = { answer = it },
                label = { Text("Answer") },
                keyboardActions = KeyboardActions(onAny = { keyboardController?.hide() })
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onEventSent(
                        EditFlashcardContract.Event.AddFlashcard(
                            FlashcardPresentation(
                                studySetName = studySetName,
                                question = question,
                                answer = answer
                            )
                        )
                    )
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        } else {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    }
                }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AddCircle, contentDescription = null)
                    Text("Add")
                }
            }
        }
    }
}

sealed class Screens(val route: String) {
    object HomeScreen: Screens("home")
    object StudySetScreen: Screens("studySet")
    object EditFlashcardScreen: Screens("editFlashcard")
}