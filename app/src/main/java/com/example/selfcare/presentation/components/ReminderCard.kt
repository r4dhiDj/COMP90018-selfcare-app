package com.example.selfcare.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.ButtonDefaults.textButtonColors
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController

@Composable
fun ReminderCard (
//    reminder: Reminder,
//    onClick: () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
            )
            .fillMaxWidth(),
//            .clickable(onClick = onClick),
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
                    text = "Reminder to sleep!", // TODO: Change to some binded string
                    modifier = Modifier,
                    style = MaterialTheme.typography.h6
                )
                Button(onClick = { /* TODO */ }) {
                    Text("Activate!")
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
                    text = "Every Day | 11:00", // frequency + time Reminder.getFrequency + Reminder.getTime
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