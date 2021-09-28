package com.example.selfcare.presentation.reminders

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.selfcare.data.model.Reminder

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
                Button(onClick = { /* TODO */ }) {
                    Text("Activate")
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp, start = 8.dp, end = 8.dp),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically
            ) {
                Text(
                    text = reminder.time, // frequency + time
                )
                TextButton(onClick = { /*TODO*/ },
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color(0xffff0000)

                    )) {
                    Text("Delete")
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun ReminderPreview() {
    ReminderCard(reminder = Reminder(0, "Feed Cat", "11:00"), navigateToReminder = {})
}