package com.ian.todolist.model

import kotlinx.serialization.Serializable

@Serializable
data class TodoTaskReqBody(
    val title: Title,
    val context: Context
)