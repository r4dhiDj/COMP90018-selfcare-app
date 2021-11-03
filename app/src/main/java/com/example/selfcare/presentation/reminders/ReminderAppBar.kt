package com.example.selfcare.presentation.reminders

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.selfcare.data.model.Reminder
import com.example.selfcare.ui.theme.IBMPlexMono
import com.example.selfcare.ui.theme.Purple700
import com.example.selfcare.util.Action

/**
 * COMP90018 - SelfCare
 * [ReminderAppBar] used to represent different states of the TopAppBar depending on the context
 * e.g. [NewReminderBar], [ExistingTaskAppBar] as well as actions on these TopAppBars
 *  [BackAction], [AddAction], [CloseAction], [DeleteAction]
 */

@Composable
fun ReminderAppBar(
    selectedReminder: Reminder?,
    navigateToReminderScreen: (Action) -> Unit
) {
    if (selectedReminder == null) {
        NewReminderBar(navigateToReminderScreen = navigateToReminderScreen)
    } else {
        ExistingTaskAppBar(
            selectedReminder = selectedReminder,
            navigateToReminderScreen = navigateToReminderScreen
        )
    }

}


@Composable
fun NewReminderBar(
    navigateToReminderScreen: (Action) -> Unit
) {
    TopAppBar (
//        navigationIcon = {
//            BackAction(onBackClicked = navigateToReminderScreen)
//        },
        title = {
            Text(
                text = "New Reminder",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = IBMPlexMono,
                fontWeight = FontWeight.Light,
                color = Color.White,
                fontSize = 18.sp
            )
        },
        backgroundColor = Purple700,
        actions = {
            AddAction(onAddClicked = navigateToReminderScreen)
        }
    )
}

@Composable
fun BackAction(
    onBackClicked: (Action) -> Unit
) {
    IconButton(onClick = {
        onBackClicked(Action.NO_ACTION) }) {
        Icon(imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back Arrow",
            tint = Color.White
        )
    }
}

@Composable
fun AddAction(
    onAddClicked: (Action) -> Unit
) {
    IconButton(onClick = {
        onAddClicked(Action.ADD) }) {
        Icon(imageVector = Icons.Filled.Check,
            contentDescription = "New Reminder",
            tint = Color.White
        )
    }
}


@Composable
fun ExistingTaskAppBar(
    selectedReminder: Reminder,
    navigateToReminderScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            CloseAction(onCloseClicked = navigateToReminderScreen)
        },
        title = {
            Text(
                text = selectedReminder.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = IBMPlexMono,
                fontWeight = FontWeight.Light,
                color = Color.White,
                fontSize = 18.sp
            )
        },
        actions = {
            DeleteAction(onDeleteClicked = navigateToReminderScreen)
            UpdateAction(onUpdateClicked = navigateToReminderScreen)
        }
    )
}

@Composable
fun CloseAction(
    onCloseClicked: (Action) -> Unit
) {
    IconButton(onClick = {
        onCloseClicked(Action.NO_ACTION) }) {
        Icon(imageVector = Icons.Filled.Close,
            contentDescription = "Close Icon",
            tint = Color.White
        )
    }
}

@Composable
fun DeleteAction(
    onDeleteClicked: (Action) -> Unit
) {
    IconButton(onClick = {
        onDeleteClicked(Action.DELETE) }) {
        Icon(imageVector = Icons.Filled.Delete,
            contentDescription = "Delete Icon",
            tint = Color.White
        )
    }
}

@Composable
fun UpdateAction(
    onUpdateClicked: (Action) -> Unit
) {
    IconButton(onClick = {
        onUpdateClicked(Action.UPDATED) }) {
        Icon(imageVector = Icons.Filled.Check,
            contentDescription = "Update Icon",
            tint = Color.White
        )
    }
}