package com.example.selfcare.presentation.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.selfcare.presentation.reminders.CreateReminderScreen
import com.example.selfcare.presentation.reminders.ReminderScreen
import com.example.selfcare.viewmodels.ReminderViewModel
import com.example.selfcare.AR_Activity
import com.example.selfcare.presentation.components.ChatScreen

@ExperimentalFoundationApi
@Composable
fun Navigation(context: Context, lifecycleOwner: LifecycleOwner) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MenuScreen.route) {
        composable(route = Screen.MenuScreen.route) {
            MenuScreen(navController = navController)
        }
        composable(
            route = Screen.ReminderScreen.route
        ) {
            val reminderViewModel = hiltViewModel<ReminderViewModel>()
            ReminderScreen(reminderViewModel, navController = navController)
        }
        composable(
            route = Screen.CreateReminderScreen.route
        ) {
            val reminderViewModel = hiltViewModel<ReminderViewModel>()
            CreateReminderScreen(reminderViewModel, navController = navController)
        }
        composable(
            route = Screen.StoreScreen.route
        ) {
            StoreScreen(navController = navController)
        }
        composable(
            route = Screen.SettingsScreen.route
        ){
            SettingsScreen(navController = navController, context, lifecycleOwner)

            //SettingsScreenVM(navController = navController, context, SettingsViewModel() )
        }
        composable(route = Screen.ARActivity.route) {
            context.startActivity(Intent(context, AR_Activity::class.java))
        }
        composable(route = Screen.ChatScreen.route) {
            ChatScreen(navController = navController)
        }

    }
}