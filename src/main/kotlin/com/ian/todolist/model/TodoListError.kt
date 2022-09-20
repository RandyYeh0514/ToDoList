package com.ian.todolist.model

import org.springframework.http.ResponseEntity

sealed class TodoListError {
    data class DatabaseError(val t: Throwable): TodoListError()

    data class InternalSerializeError(val t: Throwable): TodoListError()

    companion object {
        fun toResponse(todoListError: TodoListError): ResponseEntity<String> = when(todoListError) {
            is DatabaseError -> {
                ResponseEntity.badRequest().body("")
            }

            is InternalSerializeError -> {
                ResponseEntity.internalServerError().body("")
            }

        }
    }
}