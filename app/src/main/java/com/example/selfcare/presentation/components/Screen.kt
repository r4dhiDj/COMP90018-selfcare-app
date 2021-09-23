package com.example.selfcare.presentation.components

sealed class Screen(val route: String) {
    object MenuScreen: Screen("menu_screen")
    object ReminderScreen: Screen("reminder_screen")
    object StoreScreen: Screen("store_screen")
<<<<<<< HEAD
    object CreateReminderScreen: Screen("create_reminder_screen")
=======
    object SettingsScreen: Screen("settings_screen")
>>>>>>> 25fd0420ff873d9d564e24cab098387f16feb19c
}
