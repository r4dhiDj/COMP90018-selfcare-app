package com.example.selfcare.presentation.components

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.selfcare.presentation.reminders.CreateReminderScreen
import com.example.selfcare.presentation.reminders.ReminderScreen
import com.example.selfcare.viewmodels.ReminderViewModel
import com.example.selfcare.AR_Activity
import com.example.selfcare.presentation.reminders.ReminderNav
import com.example.selfcare.presentation.reminders.destinations.listComposable
import com.example.selfcare.presentation.reminders.destinations.reminderComposable
import com.example.selfcare.presentation.components.rendering.SettingsScreen
import com.example.selfcare.viewmodels.MainViewModel
import com.example.selfcare.presentation.components.screens.LoginScreen
import com.example.selfcare.presentation.components.screens.RegisterScreen

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun Navigation(context: Context,
               lifecycleOwner: LifecycleOwner,
               viewModel: MainViewModel,
               reminderViewModel: ReminderViewModel
) {
    val navController = rememberNavController()
    val reminderNav = remember(navController) {
        ReminderNav(navController = navController)
    }
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = Screen.RegisterScreen.route) {
        composable(route = Screen.WelcomeScreen.route){
            WelcomeScreen(viewModel = viewModel, navController = navController )
        }
        composable(route = Screen.MenuScreen.route) {
            MenuScreen(navController = navController)
        }
        listComposable(
            navigateToReminder = reminderNav.reminder,
            reminderViewModel = reminderViewModel
        )
        reminderComposable(
            navigateToReminderScreen = reminderNav.list,
            reminderViewModel = reminderViewModel
        )
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
            LoginScreen(viewModel, navController = navController, activityContext)
        }

        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(viewModel, navController = navController, activityContext)
        }

    }
}