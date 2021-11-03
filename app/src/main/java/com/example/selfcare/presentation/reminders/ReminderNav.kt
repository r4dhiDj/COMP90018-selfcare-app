package com.example.selfcare.presentation.reminders

import androidx.navigation.NavHostController
import com.example.selfcare.presentation.components.Screen
import com.example.selfcare.util.Action

/**
 * COMP90018 - SelfCare
 * Used to aid the passing of arguments between the [ReminderScreen] and [CreateReminderScreen]
 */

class ReminderNav(navController: NavHostController) {
    val list: (Action) -> Unit = { action ->
        navController.navigate("reminder_screen/${action.name}") {
            popUpTo(Screen.ReminderScreen.route) {inclusive = true}
        }
    }

    val reminder: (Int) -> Unit = { reminderId ->
        navController.navigate("create_reminder_screen/$reminderId")
    }
}