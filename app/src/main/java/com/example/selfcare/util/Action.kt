package com.example.selfcare.util


/**
 * COMP90018 - SelfCare
 * Actions used to represent changes in the reminders
 */

enum class Action {
    ADD,
    UPDATED,
    DELETE,
    DELETE_ALL,
    UNDO,
    NO_ACTION
}

fun  String?.toAction(): Action {
    return when {
        this == "ADD" -> {
            Action.ADD
        }
        this == "UPDATED" -> {
            Action.UPDATED
        }
        this == "DELETE" -> {
            Action.DELETE
        }
        this == "DELETE_ALL" -> {
            Action.DELETE_ALL
        }
        this == "UNDO" -> {
            Action.UNDO
        }
        else -> {
            Action.NO_ACTION
        }

    }
}