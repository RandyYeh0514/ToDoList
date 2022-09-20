package com.ian.todolist.model

import com.ian.todolist.utils.serializer.ObjectIdSerializer
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class TodoTask(
    val id: Id,
    val title: Title,
    val context: Context
)

@Serializable
@JvmInline
value class Id(
    @Serializable(with = ObjectIdSerializer::class)
    val value: ObjectId
)

@Serializable
@JvmInline
value class Title(val value: String)

@Serializable
@JvmInline
value class Context(val value: String)
