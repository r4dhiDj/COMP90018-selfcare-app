package com.example.selfcare.components

/**
 * COMP90018 - SelfCare
 * [Screen] stores references of each screen and their routes
 */

sealed class Screen(val route: String) {
    object MenuScreen: Screen("menu_screen")
    object ReminderScreen: Screen("reminder_screen/{action}")
    object StoreScreen: Screen("store_screen")
    object CreateReminderScreen: Screen("create_reminder_screen/{reminderId}")
    object SettingsScreen: Screen("settings_screen")
    object ARActivity: Screen("ARactivity")
    object ChatScreen: Screen("chat_screen")
    object RegisterScreen: Screen("register_screen")
    object LoginScreen: Screen("login_screen")
    object WelcomeScreen: Screen("welcome_screen")
    object BreatheScreen: Screen("breathe_screen")
}
