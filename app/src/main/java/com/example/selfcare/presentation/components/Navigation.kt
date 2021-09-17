package com.example.selfcare.presentation.components

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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
            ReminderCard(navController = navController)
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
    }
}