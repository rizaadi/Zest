package com.zephysus.core.task

enum class TaskState {
    SCHEDULED, CANCELLED, FAILED, COMPLETED;

    val isTerminalState: Boolean get() = this in listOf(CANCELLED, FAILED, COMPLETED)
}

