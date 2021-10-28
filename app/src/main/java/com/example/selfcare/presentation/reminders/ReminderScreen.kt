package com.example.selfcare.presentation.reminders

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.selfcare.data.model.Reminder
import com.example.selfcare.viewmodels.ReminderViewModel
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.selfcare.R
import com.example.selfcare.presentation.components.Screen
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
    reminderViewModel: ReminderViewModel,
    navController: NavController
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
        onUndoClicked = {
          reminderViewModel.action.value = it
        },
        reminderTitle = reminderViewModel.title.value,
        action = action
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListAppBar(
                reminderViewModel = reminderViewModel,
                navController = navController
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
    reminderViewModel: ReminderViewModel,
    navController: NavController
) {
    ReminderTopBar (
        onDeleteAllConfirmed = {
            reminderViewModel.action.value = Action.DELETE_ALL
        },
        navController = navController
    )
}

@Composable
fun ReminderTopBar(
    onDeleteAllConfirmed: () -> Unit,
    navController: NavController
) {
    TopAppBar(
        title = {
            Text(
                text = "Reminders",
                style = MaterialTheme.typography.h1
            )
        },
        actions = {
            ListBarActions (
                onDeleteAllConfirmed = onDeleteAllConfirmed
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate(Screen.MenuScreen.route)
            }) {
                Icon(Icons.Filled.ArrowBack, "backIcon", tint = Color.White)
            }
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Composable
fun ListBarActions(
    onDeleteAllConfirmed: () -> Unit
) {

    var openDialog by remember { mutableStateOf(false) }

    DeleteAllDialog(
        title = stringResource(id = R.string.delete_all_reminders),
        message = stringResource(id = R.string.delete_all_reminders_confirmation),
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = { onDeleteAllConfirmed() }
    )

    DeleteAllAction(onDeleteAllConfirmed = {openDialog = true})
}

@Composable
fun DeleteAllAction (
    onDeleteAllConfirmed: () -> Unit
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
                    onDeleteAllConfirmed()
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
    Spacer(modifier = Modifier
        .height(16.dp))
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
    onUndoClicked: (Action) -> Unit,
    reminderTitle: String,
    action: Action
) {
    handleDatabaseActions()

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = setMessage(action = action, reminderTitle = reminderTitle),
                    actionLabel = setActionLabel(action = action)
                )
                undoDeletedTask(
                    action = action,
                    snackBarResult = snackbarResult,
                    onUndoClicked = onUndoClicked
                )
            }
        }
    }
}

private fun setMessage(action: Action, reminderTitle: String) : String {
    return when(action) {
        Action.DELETE_ALL -> "All Reminders Removed."
        else -> "${action.name}: $reminderTitle"
    }
}

private fun setActionLabel(action: Action): String {
    return if(action.name == "DELETE") {
        "UNDO"
    } else {
        "OK"
    }
}

private fun undoDeletedTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
        onUndoClicked(Action.UNDO)
    }
}
