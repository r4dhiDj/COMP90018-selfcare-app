package com.example.selfcare.presentation.components

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.selfcare.presentation.reminders.CreateReminderScreen
import com.example.selfcare.presentation.reminders.ReminderScreen
import com.example.selfcare.viewmodels.ReminderViewModel

@ExperimentalFoundationApi
@Composable
fun Navigation(context: Context) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MenuScreen.route) {
        composable(route = Screen.MenuScreen.route) {
            MenuScreen(navController = navController)
        }
        composable(
            route = Screen.ReminderScreen.route
        ) {
<<<<<<< HEAD
            val reminderViewModel = hiltViewModel<ReminderViewModel>()
            ReminderScreen(reminderViewModel, navController = navController)
        }
        composable(
            route = Screen.CreateReminderScreen.route
        ) {
            val reminderViewModel = hiltViewModel<ReminderViewModel>()
            CreateReminderScreen(reminderViewModel, navController = navController)
=======
            ReminderCard(navController = navController)
>>>>>>> 25fd0420ff873d9d564e24cab098387f16feb19c
        }
        composable(
            route = Screen.StoreScreen.route
        ) {
            StoreScreen(navController = navController)
        }
<<<<<<< HEAD

=======
        composable(
            route = Screen.SettingsScreen.route
        ){
            SettingsScreen(navController = navController, context)
        }
>>>>>>> 25fd0420ff873d9d564e24cab098387f16feb19c
    }
}