package com.example.selfcare.presentation.reminders

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.selfcare.MainActivity
import com.example.selfcare.viewmodels.ReminderViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

@Composable
fun ReminderContent (
    title: String,
    onTitleChange: (String) -> Unit,
    time: String,
    onTimeChange: (String) -> Unit,
    reminderViewModel: ReminderViewModel
) {

    val activity = LocalContext.current as MainActivity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { onTitleChange(it) },
            label = { Text("Title") },
            textStyle = MaterialTheme.typography.body1
        )
        Divider(
            modifier = Modifier
                .height(8.dp),
            color = MaterialTheme.colors.background
        )
        TimePicker(
            activity = activity,
            reminderViewModel = reminderViewModel,
            time = time,
            onTimeChange = { onTimeChange(it) }
        )


    }
}



@Composable
fun TimePicker(
    activity: MainActivity,
    reminderViewModel: ReminderViewModel,
    time: String,
    onTimeChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .height(60.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
                shape = MaterialTheme.shapes.small
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            // TODO: Fix This
            text = time,
            modifier = Modifier
                .weight(8f)
                .padding(start = 24.dp),
        )
        IconButton(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .weight(2f),
            onClick = { showTimePicker(
                activity = activity,
                reminderViewModel = reminderViewModel,
                onTimeChange = onTimeChange
            ) }
        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Time Picker"
            )

        }


    }



}

/**
 * Displays the time picker to the user
 */

fun showTimePicker(
    activity: MainActivity,
    reminderViewModel: ReminderViewModel,
    onTimeChange: (String) -> Unit
) {

    // Obtains the current time of the system
    reminderViewModel.getCurrentTime()

    // Builds the time picker
    val picker = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_24H)
        .setHour(reminderViewModel.hour)
        .setMinute(reminderViewModel.minute)
        .setTitleText("Select Reminder Time")
        .build()

    picker.show(activity.supportFragmentManager, picker.toString())

    // Sets the ViewModel's attributes according to the picker's time
    picker.addOnPositiveButtonClickListener{
        onTimeChange(String.format("%02d:%02d", picker.hour, picker.minute))
    }

}