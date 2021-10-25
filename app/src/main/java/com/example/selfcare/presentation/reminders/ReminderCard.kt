package com.example.selfcare.presentation.reminders

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import com.example.selfcare.data.model.Reminder

@Composable
fun ReminderCard (
    reminder: Reminder
) {

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
            )
            .fillMaxWidth(),
            elevation = 8.dp
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
                    text = reminder.title,
                    modifier = Modifier,
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
                // W0W
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

//@Preview
//@Composable
//fun ComposablePreview() {
//    ReminderCard()
//}