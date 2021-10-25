package com.example.selfcare.presentation.reminders

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.selfcare.data.model.Reminder
import com.example.selfcare.presentation.components.Screen
import com.example.selfcare.viewmodels.ReminderViewModel

/**
 * [ReminderScreen] is the key screen to display the Reminders to the user and for them to edit and update
 * All of this data is formatted in a way that
 * the reminder screen can take the information and display it
 */

@Composable
fun ReminderScreen (reminderViewModel: ReminderViewModel = viewModel(),
                    navController: NavController
) {

    val reminders = reminderViewModel.reminders.value

    //TODO: For testing - to delete
    for (reminder in reminders) {
        Log.d("APPDEBUG", "ReminderScreen: ${reminder}")
    }

    ReminderList(reminders, navController = navController)

}


@Composable
fun ReminderList(
    reminders: List<Reminder>,
    navController: NavController
) {

    // Convert to Lazy Column
    Column {
        LazyColumn {
            items(reminders) {
                reminder -> ReminderCard(reminder = reminder)
            }
        }

        // Implement to Create Reminder screen
        Button(onClick = { navController.navigate(Screen.CreateReminderScreen.route) },
            modifier = Modifier
            .align(Alignment.CenterHorizontally)) {
            Text(text = "Add Reminder")
        }

    }

}

//@Preview
//@Composable
//private fun ReminderScreen() {
////    val previewState = ReminderViewState(
////        reminders = defaultReminders
////    )
//    ReminderList(defaultReminders, rememberNavController())
//}