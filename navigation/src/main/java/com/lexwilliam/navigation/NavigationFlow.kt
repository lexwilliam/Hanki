package com.lexwilliam.navigation

sealed class NavigationFlow {
    object HomeFlow : NavigationFlow()
    object AuthFlow : NavigationFlow()
}