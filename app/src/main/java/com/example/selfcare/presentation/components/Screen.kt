package com.example.selfcare.presentation.components

sealed class Screen(val route: String) {
    object MenuScreen: Screen("menu_screen")
    object ReminderScreen: Screen("reminder_screen")
    object StoreScreen: Screen("store_screen")
    object CreateReminderScreen: Screen("create_reminder_screen")
}
