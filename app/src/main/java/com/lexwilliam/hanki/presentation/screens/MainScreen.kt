package com.lexwilliam.hanki.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lexwilliam.hanki.presentation.navigation.BottomNavigationScreens
import com.lexwilliam.hanki.presentation.screens.home.HomeScreen
import com.lexwilliam.hanki.presentation.screens.study_set.StudySetScreen
import com.lexwilliam.hanki.presentation.viewmodel.HomeViewModel
import com.lexwilliam.hanki.presentation.viewmodel.StudySetViewModel
import com.lexwilliam.hanki.presentation.viewmodel.StudySetViewModel_Factory

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
private fun MainScreenNavigationConfigurations(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(navController = navController, startDestination = "home") {
        composable(Screens.HomeScreen.route) {
            InitHomeScreen()
        }
        composable(Screens.StudySetScreen.route) {
            InitStudySetScreen()
        }
    }
}

@Composable
private fun InitHomeScreen() {
    val homeViewModel: HomeViewModel = hiltViewModel()
    HomeScreen(state = homeViewModel.viewState.value)
}

@Composable
private fun InitStudySetScreen() {
    val studySetViewModel: StudySetViewModel = hiltViewModel()
    StudySetScreen(state = studySetViewModel.viewState.value)
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

sealed class Screens(val route: String) {
    object HomeScreen: Screens("home")
    object StudySetScreen: Screens("studySet")
}