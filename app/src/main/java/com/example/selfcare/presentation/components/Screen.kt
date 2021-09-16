package com.example.selfcare.presentation.components

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object ReminderScreen: Screen("reminder_screen")
}
