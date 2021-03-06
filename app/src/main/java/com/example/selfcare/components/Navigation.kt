package com.example.selfcare.components

import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Vibrator
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.selfcare.viewmodels.ReminderViewModel
import com.example.selfcare.AR_Activity
import com.example.selfcare.components.reminders.ReminderNav
import com.example.selfcare.components.reminders.destinations.listComposable
import com.example.selfcare.components.reminders.destinations.reminderComposable
import com.example.selfcare.viewmodels.MainViewModel
import com.example.selfcare.R
import com.example.selfcare.components.chat.ChatScreen
import com.example.selfcare.components.screens.LoginScreen
import com.example.selfcare.components.screens.RegisterScreen
import com.example.selfcare.service.AlarmService
import com.example.selfcare.viewmodels.BreatheViewModel


@RequiresApi(Build.VERSION_CODES.Q)
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun Navigation(activityContext: ComponentActivity,
               viewModel: MainViewModel,
               reminderViewModel: ReminderViewModel,
               alarmService: AlarmService
) {
    val navController = rememberNavController()
    val reminderNav = remember(navController) {
        ReminderNav(navController = navController)
    }
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(route = Screen.WelcomeScreen.route){
            WelcomeScreen(viewModel = viewModel, navController = navController )
        }
        composable(route = Screen.MenuScreen.route) {
            MenuScreen(viewModel = viewModel, navController = navController)
        }
        listComposable(
            navigateToReminder = reminderNav.reminder,
            reminderViewModel = reminderViewModel,
            navController = navController
        )
        reminderComposable(
            navigateToReminderScreen = reminderNav.list,
            reminderViewModel = reminderViewModel,
            alarmService = alarmService
        )
        composable(
            route = Screen.StoreScreen.route
        ) {
            StoreScreen(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.SettingsScreen.route
        ){
            SettingsScreen(viewModel, navController = navController, activityContext = activityContext)

        }
//        composable(route = Screen.ARActivity.route) {
//            context.startActivity(Intent(context, AR_Activity::class.java))
//        }
        composable(route = Screen.ChatScreen.route) {
            ChatScreen(navController = navController)
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(viewModel, navController = navController, activityContext)
        }

        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(viewModel, navController = navController, activityContext)
        }
        composable(route = Screen.BreatheScreen.route) {
            val breatheVM = hiltViewModel<BreatheViewModel>()
            val vibrator = context.getSystemService(VIBRATOR_SERVICE) as Vibrator
            val mediaPlayer = MediaPlayer.create(context, R.raw.relaxing).apply{isLooping = true}
            BreatheScreen(navController,breatheVM,vibrator,mediaPlayer,viewModel)
        }

    }
}