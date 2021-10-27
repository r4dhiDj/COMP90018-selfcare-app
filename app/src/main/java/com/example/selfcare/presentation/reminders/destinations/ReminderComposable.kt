package com.example.selfcare.presentation.reminders.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.selfcare.constants.Constants
import com.example.selfcare.constants.Constants.REMINDER_ARGUMENT_KEY
import com.example.selfcare.presentation.components.Screen
import com.example.selfcare.presentation.reminders.CreateReminderScreen
import com.example.selfcare.presentation.reminders.ReminderScreen
import com.example.selfcare.service.AlarmService
import com.example.selfcare.util.Action
import com.example.selfcare.viewmodels.ReminderViewModel

fun NavGraphBuilder.reminderComposable(
    navigateToReminderScreen: (Action) -> Unit,
    reminderViewModel: ReminderViewModel,
    alarmService: AlarmService
){
    composable(
        route = Screen.CreateReminderScreen.route,
        arguments = listOf(navArgument(REMINDER_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val reminderId = navBackStackEntry.arguments!!.getInt(REMINDER_ARGUMENT_KEY)
        // Uses the ID to request the reminder from the database


        LaunchedEffect(key1 = reminderId) {
            reminderViewModel.getSelectedReminder(reminderId = reminderId)
        }

        val selectedReminder by reminderViewModel.selectedReminder.collectAsState()
        
        LaunchedEffect(key1 = selectedReminder) {
            if (selectedReminder != null || reminderId == -1 ) {
                reminderViewModel.updateReminderFields(reminder = selectedReminder)
            }
        }

        CreateReminderScreen(
            selectedReminder = selectedReminder,
            navigateToReminderScreen = navigateToReminderScreen,
            reminderViewModel = reminderViewModel,
            alarmService = alarmService
        )

    }
}