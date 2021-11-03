package com.example.selfcare.components.reminders

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.selfcare.data.model.Reminder

/**
 * COMP90018 - SelfCare
 * [ReminderCard] represents a single reminder object as a composable, contains information of
 * the reminder including the Title and Time
 */

@ExperimentalMaterialApi
@Composable
fun ReminderCard (
    reminder: Reminder,
    navigateToReminder: (reminderId: Int) -> Unit
) {

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
            )
            .fillMaxWidth(),
            elevation = 8.dp,
        onClick = {
            navigateToReminder(reminder.id)
        }
    ) {
        Column{
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier,
                    text = reminder.title,
                    style = MaterialTheme.typography.h6
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically
            ) {
                Text(
                    text = reminder.time
                )
            }
        }
    }
}

