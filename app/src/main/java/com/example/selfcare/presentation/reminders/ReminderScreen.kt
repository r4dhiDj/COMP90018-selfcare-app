package com.example.selfcare.presentation.reminders

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.selfcare.data.model.Reminder
import com.example.selfcare.presentation.components.Screen
import com.example.selfcare.viewmodels.ReminderViewModel
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.selfcare.R
import com.example.selfcare.ui.theme.Typography
import com.example.selfcare.util.Action
import com.example.selfcare.util.RequestState
import kotlinx.coroutines.launch

/**
 * [ReminderScreen] is the key screen to display the Reminders to the user and for them to edit and update
 * All of this data is formatted in a way that
 * the reminder screen can take the information and display it
 */

@ExperimentalMaterialApi
@Composable
fun ReminderScreen (
    navigateToReminder: (reminderId: Int) -> Unit,
    reminderViewModel: ReminderViewModel
) {

    LaunchedEffect(key1 = true){
        reminderViewModel.getAllReminders()
    }

    val action by reminderViewModel.action

    val allReminders by reminderViewModel.allReminders.collectAsState()

    val scaffoldState = rememberScaffoldState()

    DisplaySnackBar(
        scaffoldState = scaffoldState,
        handleDatabaseActions = { reminderViewModel.handleDatabaseActions(action = action) },
        reminderTitle = reminderViewModel.title.value,
        action = action
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListAppBar(
                reminderViewModel = reminderViewModel
            )
        },
        content = {
                  ReminderList(
                      reminders = allReminders,
                      navigateToReminder = navigateToReminder
                  )
        },
        floatingActionButton = {
            ReminderFab(onFabClicked = navigateToReminder)
        }
    )


}

@Composable
fun ReminderFab(
    onFabClicked: (reminderId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFabClicked(-1)
        }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add Button",
            tint = Color.White
        )
    }
}

@Composable
fun ListAppBar(
    reminderViewModel: ReminderViewModel
) {
    ReminderTopBar (
        onDeleteClicked = {}
    )
}

@Composable
fun ReminderTopBar(
    onDeleteClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = "Reminders")
        },
        actions = {
            ListBarActions (
                onDeleteClicked = onDeleteClicked
            )
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Composable
fun ListBarActions(
    onDeleteClicked: () -> Unit
) {
    DeleteAllAction(onDeleteClicked = onDeleteClicked)
}

@Composable
fun DeleteAllAction (
    onDeleteClicked: () -> Unit
) {
    var expanded by remember { mutableStateOf(false)}

    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_vertical_menu),
            contentDescription = "Delete All",
            tint = MaterialTheme.colors.primaryVariant
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onDeleteClicked()
                }
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Delete All",
                    style = Typography.subtitle2
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun ReminderList(
    reminders: RequestState<List<Reminder>>,
    navigateToReminder: (reminderId: Int) -> Unit
) {
    if (reminders is RequestState.Success) {
        if (reminders.data.isEmpty()) {
            EmptyContent()
        } else {
            DisplayReminders(
                reminders = reminders.data,
                navigateToReminder = navigateToReminder
            )
        }
    }



}

@ExperimentalMaterialApi
@Composable
fun DisplayReminders (
    reminders: List<Reminder>,
    navigateToReminder: (reminderId: Int) -> Unit
) {
    LazyColumn {
        items(
            items = reminders,
            key = { reminder ->
                reminder.id
            }
        ) { reminder ->
            ReminderCard(
                reminder = reminder,
                navigateToReminder = navigateToReminder
            )
        }
    }
}

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    handleDatabaseActions: () -> Unit,
    reminderTitle: String,
    action: Action
) {
    handleDatabaseActions()

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = "${action.name}: $reminderTitle",
                    actionLabel = "OK"
                )
            }
        }
    }


}
