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
import com.example.selfcare.presentation.reminders.destinations.listComposable
import com.example.selfcare.service.AlarmService

/**
 * COMP90018 - SelfCare
 * [CreateReminderScreen] represents the screen to create a new reminder. Also used to schedule
 * notifications via the [alarmService]
 */

@Composable
fun CreateReminderScreen (
    selectedReminder: Reminder?,
    navigateToReminderScreen: (Action) -> Unit,
    reminderViewModel: ReminderViewModel,
    alarmService: AlarmService
) {

    val title: String by reminderViewModel.title
    val time: String by reminderViewModel.time
    val text: String by reminderViewModel.text
    val context = LocalContext.current

    Scaffold(
        topBar = {
            ReminderAppBar(
                selectedReminder = selectedReminder,
                navigateToReminderScreen = { action ->
                    if (action == Action.NO_ACTION) {
                        navigateToReminderScreen(action)
                    } else if (action == Action.ADD || action == Action.UPDATED) {
                        if (reminderViewModel.validateFields()) {
                            reminderViewModel.setAlarm(
                                context = context
                            ) {timeInMillis -> alarmService.setExactAlarm(
                                timeInMillis,
                                reminderViewModel.title.value,
                                reminderViewModel.text.value
                            )}
                            navigateToReminderScreen(action)
                        } else {
                            displayToast(context)
                        }
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
                text = text,
                onTextChange = {
                    reminderViewModel.text.value = it
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




