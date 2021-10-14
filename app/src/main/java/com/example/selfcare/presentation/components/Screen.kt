package com.example.selfcare.presentation.components

sealed class Screen(val route: String) {
    object MenuScreen: Screen("menu_screen")
    object ReminderScreen: Screen("reminder_screen")
    object StoreScreen: Screen("store_screen")
    object CreateReminderScreen: Screen("create_reminder_screen")
    object SettingsScreen: Screen("settings_screen")
    object ARActivity: Screen("ARactivity")
    object ChatScreen: Screen("chat_screen")
    object WelcomeScreen: Screen("welcome_screen")
    object BreatheScreen: Screen("breathe_screen")
}
