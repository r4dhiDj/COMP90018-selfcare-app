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
import com.example.selfcare.presentation.components.CreateReminder
import com.example.selfcare.presentation.components.MenuScreen
import com.example.selfcare.presentation.components.Navigation
import com.example.selfcare.presentation.components.ReminderCard
import com.example.selfcare.ui.theme.SelfCareTheme

class MainActivity : FragmentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SelfCareTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
//                    ReminderCard()
//                    CreateReminder(activity = this)
//                    MenuScreen()
                    Navigation()
                }
            }
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
//    SelfCareTheme {
//        Greeting("Android")
//    }
//    ReminderCard()

}