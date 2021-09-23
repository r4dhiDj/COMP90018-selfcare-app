package com.example.selfcare.presentation.reminders

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.selfcare.MainActivity
import com.example.selfcare.R
import com.example.selfcare.viewmodels.ReminderViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat


@Composable
fun CreateReminderScreen (
    reminderViewModel: ReminderViewModel,
    navController: NavController
) {

    // text value saved in reminder viewmodel
    val reminderText = reminderViewModel.reminderText
    val activity = LocalContext.current as MainActivity

    Card(
        modifier = Modifier
            .padding(top = 12.dp, bottom = 12.dp, start = 20.dp, end = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(top = 12.dp, bottom = 12.dp, start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = "Reminder",
                modifier = Modifier
            )
            OutlinedTextField(
                value = reminderText,
                onValueChange = { newValue ->
                    reminderViewModel.onTextChanged(newValue)
                                },
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp, start = 8.dp, end = 8.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Set Time"
            )

            Button(
                onClick = { showTimePicker(activity, reminderViewModel)},
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Select Time")
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_timer_24),
                    contentDescription = "store",
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
            }

            Text(
                text = "Confirm"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                Arrangement.SpaceEvenly
            ) {
                Button(onClick = { reminderViewModel.createReminder() }) {
                    Text(text = "OK")
                }
            }

        }
    }
}

@Preview
@Composable
fun ComposablePreview() {
    Button(
        onClick = { }
    ) {
        Text(text = "Select Time")
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_timer_24),
            contentDescription = "store",
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(20.dp))
        )
    }
}


/**
 * Displays the time picker to the user
 */

fun showTimePicker(activity: MainActivity,
reminderViewModel: ReminderViewModel
) {

    // Obtains the current time of the system
    reminderViewModel.getCurrentTime()
    Log.d("TIME PICKER", "showTimePicker: ${reminderViewModel.hour} ")

    // Builds the time picker
    val picker = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_12H)
        .setHour(reminderViewModel.hour)
        .setMinute(reminderViewModel.minute)
        .setTitleText("Select Reminder Time")
        .build()

    picker.show(activity.supportFragmentManager, picker.toString())

    // Sets the ViewModel's attributes according to the picker's time
    picker.addOnPositiveButtonClickListener{
        reminderViewModel.savedHour = picker.hour
        reminderViewModel.savedMinute = picker.minute
    }

}