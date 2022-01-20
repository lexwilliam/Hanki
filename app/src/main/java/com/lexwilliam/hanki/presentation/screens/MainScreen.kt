package com.lexwilliam.hanki.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
import com.lexwilliam.hanki.model.StudySetPresentation
import com.lexwilliam.hanki.presentation.navigation.BottomNavigationScreens
import com.lexwilliam.hanki.presentation.screens.home.HomeContract
import com.lexwilliam.hanki.presentation.screens.home.HomeScreen
import com.lexwilliam.hanki.presentation.screens.study_set.StudySetScreen
import com.lexwilliam.hanki.presentation.viewmodel.HomeViewModel
import com.lexwilliam.hanki.presentation.viewmodel.StudySetViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
                modifier = Modifier,
                navController = navController,
                items = bottomNavigationItems
            )
        }
    ) { innerPadding ->
        MainScreenNavigationConfigurations(navController, innerPadding)
    }

}

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
                navToAddFlashcard = {
                    navController.navigate(Screens.AddFlashcardScreen.route)
                }
            )
        }
    }
}

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
    navToAddFlashcard: () -> Unit
) {
    val studySetViewModel: StudySetViewModel = hiltViewModel()
    StudySetFloatingActionBtn(
        studySetViewModel = studySetViewModel,
        onBackStackPressed = { onBackStackPressed() },
        onFabClick = { navToAddFlashcard() }
    )
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun HankiBottomNavigation(
    modifier: Modifier,
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
            BottomTab(modifier, screen, currentRoute, navController)
        }
    }
}

@Composable
private fun RowScope.BottomTab(
    modifier: Modifier,
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
            FloatingActionButton(onClick = { onFabClick() }) {
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

@ExperimentalMaterialApi
@Composable
fun HomeBottomSheetContent(
    modifier: Modifier = Modifier,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    coroutineScope: CoroutineScope,
    onEventSent: (event: HomeContract.Event) -> Unit
) {
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
                onValueChange = { setName = it }
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

sealed class Screens(val route: String) {
    object HomeScreen: Screens("home")
    object StudySetScreen: Screens("studySet")
    object AddFlashcardScreen: Screens("addFlashcard")
}