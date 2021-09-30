package com.example.selfcare.presentation.reminders

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.selfcare.MainActivity
import com.example.selfcare.R
import com.example.selfcare.data.model.Reminder
import com.example.selfcare.util.Action
import com.example.selfcare.viewmodels.ReminderViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat


@Composable
fun CreateReminderScreen (
    selectedReminder: Reminder?,
    navigateToReminderScreen: (Action) -> Unit,
    reminderViewModel: ReminderViewModel
) {

    Scaffold(
        topBar = {
            ReminderAppBar(
                selectedReminder = selectedReminder,
                navigateToReminderScreen = navigateToReminderScreen
            )
        },
        content = {
            ReminderContent(
                title = "",
                onTitleChange = {},
                time = "",
                onTimeChange = {},
                reminderViewModel = reminderViewModel
            )

        }
    )


}



//@Preview
//@Composable
//fun ComposablePreview() {
//    Button(
//        onClick = { }
//    ) {
//        Text(text = "Select Time")
//        Icon(
//            painter = painterResource(id = R.drawable.ic_baseline_timer_24),
//            contentDescription = "store",
//            modifier = Modifier
//                .padding(10.dp)
//                .clip(RoundedCornerShape(20.dp))
//        )
//    }
//}


