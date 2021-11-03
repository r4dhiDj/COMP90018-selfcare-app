package com.example.selfcare.presentation.reminders.destinations

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.selfcare.constants.Constants.LIST_ARGUMENT_KEY
import com.example.selfcare.presentation.components.Screen
import com.example.selfcare.presentation.reminders.ReminderScreen
import com.example.selfcare.util.toAction
import com.example.selfcare.viewmodels.ReminderViewModel

/**
 * COMP90018 - SelfCare
 * [listComposable] represents navigation to [ReminderScreen] and handles the navigation
 */


@ExperimentalMaterialApi
fun NavGraphBuilder.listComposable(
    navigateToReminder: (reminderId: Int) -> Unit,
    reminderViewModel: ReminderViewModel,
    navController: NavController
){
    composable(
        route = Screen.ReminderScreen.route,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) {
        navBackStackEntry ->
        val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()

        LaunchedEffect(key1 = action) {
            reminderViewModel.action.value = action
        }

        ReminderScreen(
            navigateToReminder = navigateToReminder,
            reminderViewModel = reminderViewModel,
            navController = navController
        )
    }
}