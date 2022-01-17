package com.lexwilliam.hanki.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.lexwilliam.hanki.R
import com.lexwilliam.hanki.presentation.screens.Screens

sealed class BottomNavigationScreens(
    val route: String,
    val description: String,
    @DrawableRes val drawableRes: Int

) {
    object Home : BottomNavigationScreens(
        Screens.HomeScreen.route,
        "Home",
        R.drawable.ic_baseline_home_24
    )
}