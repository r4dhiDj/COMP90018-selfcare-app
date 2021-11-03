package com.example.selfcare

import android.os.Build
import android.os.Bundle

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.example.selfcare.components.Navigation
import com.example.selfcare.ui.theme.SelfCareTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.runtime.LaunchedEffect
//import androidx.lifecycle.asLiveData
import com.example.selfcare.viewmodels.ReminderViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.selfcare.service.AlarmService
import com.example.selfcare.util.createNotificationChannel
import com.example.selfcare.viewmodels.MainViewModel

/**
 * COMP90018 - SelfCare
 * [MainActivity] represent main entry point of the application and loads [ReminderViewModel] and
 * [alarmService]
 */


@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // Instantiate reminderViewModel and alarmService
    private val reminderViewModel: ReminderViewModel by viewModels()
    lateinit var alarmService: AlarmService

    @RequiresApi(Build.VERSION_CODES.Q)
    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alarmService = AlarmService(this)
        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            AppMain(viewModel)
        }
    }

    @ExperimentalMaterial3Api
    @RequiresApi(Build.VERSION_CODES.Q)
    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    @Composable
    private fun AppMain(viewModel: MainViewModel){
        LaunchedEffect(false) {
            viewModel.getDarkMode()
        }
        val darkMode = viewModel.darkModeState.value
        SelfCareTheme(darkMode = darkMode) {
            Surface(color = MaterialTheme.colors.background) {
                createNotificationChannel(this)
                Navigation(
                    this,
                    viewModel = viewModel,
                    reminderViewModel = reminderViewModel,
                    alarmService
                )
            }
        }

    }
}
