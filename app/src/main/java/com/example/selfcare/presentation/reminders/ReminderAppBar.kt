package com.example.selfcare.presentation.reminders

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.selfcare.data.model.Reminder
import com.example.selfcare.util.Action

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
        navigationIcon = {
            BackAction(onBackClicked = navigateToReminderScreen)
        },
        title = {
            Text(
                text = "New Reminder"
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
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
            contentDescription = "Back Arrow"
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
            contentDescription = "New Reminder"
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
                overflow = TextOverflow.Ellipsis
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
        )
    }
}

@Composable
fun UpdateAction(
    onUpdateClicked: (Action) -> Unit
) {
    IconButton(onClick = {
        onUpdateClicked(Action.UPDATE) }) {
        Icon(imageVector = Icons.Filled.Check,
            contentDescription = "Update Icon",
        )
    }
}