package com.example.selfcare.presentation.components

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.selfcare.presentation.reminders.CreateReminderScreen
import com.example.selfcare.presentation.reminders.ReminderScreen
import com.example.selfcare.viewmodels.ReminderViewModel
import com.example.selfcare.AR_Activity
import com.example.selfcare.presentation.components.rendering.SettingsScreen
import com.example.selfcare.viewmodels.MainViewModel
import com.example.selfcare.presentation.components.screens.LoginScreen
import com.example.selfcare.presentation.components.screens.RegisterScreen

@ExperimentalFoundationApi
@Composable
fun Navigation(viewModel: MainViewModel, activityContext: ComponentActivity) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = Screen.RegisterScreen.route) {
        composable(route = Screen.WelcomeScreen.route){
            WelcomeScreen(viewModel = viewModel, navController = navController )
        }
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
            SettingsScreen(viewModel, navController = navController)

            //SettingsScreenVM(navController = navController, context, SettingsViewModel() )
        }
        composable(route = Screen.ARActivity.route) {
            context.startActivity(Intent(context, AR_Activity::class.java))
        }
        composable(route = Screen.ChatScreen.route) {
            ChatScreen(navController = navController)
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController, activityContext)
        }

        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController, activityContext)
        }

    }
}