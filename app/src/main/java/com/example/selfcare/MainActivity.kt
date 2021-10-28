package com.example.selfcare

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.selfcare.presentation.components.Navigation
import com.example.selfcare.presentation.components.*
import com.example.selfcare.ui.theme.SelfCareTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.asLiveData

import androidx.compose.runtime.LaunchedEffect
//import androidx.lifecycle.asLiveData
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.selfcare.viewmodels.ReminderViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.selfcare.data.local.SettingsDataStoreImpl
import com.example.selfcare.service.AlarmService
import com.example.selfcare.viewmodels.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
   // @ExperimentalFoundationApi
    //lateinit var settingsDataStoreImpl: SettingsDataStoreImpl
    //val isDarkMode =  mutableStateOf(true)

    // Instantiate ViewModels
    private val reminderViewModel: ReminderViewModel by viewModels()
    lateinit var alarmService: AlarmService

    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //settingsDataStore = SettingsDataStore(this)
        //setInitialTheme()
        //observeData()
        alarmService = AlarmService(this)
        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            AppMain(viewModel)
        }
    }

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






            /**
            SelfCareTheme(isDarkMode.value) {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    createNotificationChannel(this)
                    Navigation(
                        this,
                        this,
                        reminderViewModel = reminderViewModel
                    )

                }
            }
        }
    }


    @ExperimentalFoundationApi
    private fun observeData() {
        settingsDataStore.darkModeFlow.asLiveData().observe(this, {
            isDarkMode.value = it
        })
    }

    @ExperimentalFoundationApi
    private fun setInitialTheme(){
        GlobalScope.launch{
            settingsDataStore.storeDarkMode(
                false)
        }
    }
}
    */

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}