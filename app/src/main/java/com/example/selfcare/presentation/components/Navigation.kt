package com.example.selfcare.presentation.components

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import android.media.MediaPlayer
import android.os.Vibrator
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.selfcare.presentation.reminders.CreateReminderScreen
import com.example.selfcare.presentation.reminders.ReminderScreen
import com.example.selfcare.viewmodels.ReminderViewModel
import com.example.selfcare.AR_Activity
import com.example.selfcare.R
import com.example.selfcare.viewmodels.BreatheViewModel

@ExperimentalFoundationApi
@Composable
fun Navigation(context: Context) {
    val navController = rememberNavController()
    val NavContext = LocalContext.current
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
            SettingsScreen(navController = navController, context)
        }
        composable(route = Screen.ARActivity.route) {
            context.startActivity(Intent(NavContext, AR_Activity::class.java))
        }
        composable(route = Screen.BreatheScreen.route) {
            val breatheVM = hiltViewModel<BreatheViewModel>()
            val vibrator = context.getSystemService(VIBRATOR_SERVICE) as Vibrator
            val mediaPlayer = MediaPlayer.create(context, R.raw.relaxing).apply{isLooping = true}
            BreatheScreen(navController,breatheVM,vibrator,mediaPlayer)
        }

    }
}