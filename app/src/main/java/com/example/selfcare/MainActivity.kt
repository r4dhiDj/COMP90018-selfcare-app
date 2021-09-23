package com.example.selfcare

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import com.example.selfcare.presentation.components.Navigation
import com.example.selfcare.presentation.components.*
import com.example.selfcare.ui.theme.SelfCareTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import com.example.selfcare.data.SettingsDataStore
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @ExperimentalFoundationApi
    lateinit var settingsDataStore: SettingsDataStore
    val isDarkMode =  mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsDataStore = SettingsDataStore(this)
        setInitialTheme()
        observeData()
        setContent {
            SelfCareTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    createNotificationChannel(this)
                    Navigation(this)

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
            settingsDataStore.storeUser(
                false)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}