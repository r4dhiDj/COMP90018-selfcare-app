package com.example.selfcare.presentation.reminders


import android.content.Context
import android.widget.Toast
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.example.selfcare.data.model.Reminder
import com.example.selfcare.util.Action
import com.example.selfcare.viewmodels.ReminderViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext


@Composable
fun CreateReminderScreen (
    selectedReminder: Reminder?,
    navigateToReminderScreen: (Action) -> Unit,
    reminderViewModel: ReminderViewModel
) {

    val title: String by reminderViewModel.title
    val time: String by reminderViewModel.time
    val context = LocalContext.current

    Scaffold(
        topBar = {
            ReminderAppBar(
                selectedReminder = selectedReminder,
                navigateToReminderScreen = { action ->
                    if (action == Action.NO_ACTION) {
                        navigateToReminderScreen(action)
                    } else {
                        if (reminderViewModel.validateFields()) {
                            navigateToReminderScreen(action)
                        } else {
                            displayToast(context)
                        }
                    }

                }
            )
        },
        content = {
            ReminderContent(
                title = title,
                onTitleChange = {
                    reminderViewModel.updateTitle(it)
                },
                time = time,
                onTimeChange = {
                    reminderViewModel.time.value = it
                },
                reminderViewModel = reminderViewModel
            )

        }
    )


}

fun displayToast(context: Context) {
    Toast.makeText(
        context,
        "Some fields are empty.",
        Toast.LENGTH_SHORT
    ).show()
}




