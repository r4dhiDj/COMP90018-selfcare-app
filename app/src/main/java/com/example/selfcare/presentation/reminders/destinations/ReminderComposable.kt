package com.example.selfcare.presentation.reminders.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.selfcare.constants.Constants
import com.example.selfcare.presentation.components.Screen
import com.example.selfcare.util.Action

fun NavGraphBuilder.reminderComposable(
    navigateToReminder: (Action) -> Unit
){
    composable(
        route = Screen.CreateReminderScreen.route,
        arguments = listOf(navArgument(Constants.REMINDER_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) {

    }
}