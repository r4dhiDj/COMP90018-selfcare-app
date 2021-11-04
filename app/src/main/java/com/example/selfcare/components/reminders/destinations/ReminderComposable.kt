package com.example.selfcare.components.reminders.destinations


import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.selfcare.constants.Constants.REMINDER_ARGUMENT_KEY
import com.example.selfcare.components.Screen
import com.example.selfcare.components.reminders.CreateReminderScreen
import com.example.selfcare.service.AlarmService
import com.example.selfcare.util.Action
import com.example.selfcare.viewmodels.ReminderViewModel

/**
 * COMP90018 - SelfCare
 * [reminderComposable] represents navigation to [CreateReminderScreeen] and handles the navigation
 * in between reminders via passing a [reminderId]. Also passes in [alarmService] to handle
 * scheduling of notifications
 */


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