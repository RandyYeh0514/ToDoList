package com.ian.todolist.controller

import com.ian.todolist.model.TodoListError
import com.ian.todolist.model.TodoTask
import com.ian.todolist.model.TodoTaskReqBody
import com.ian.todolist.service.TodoListService
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class MainController {

    @Autowired
    private lateinit var todoListService: TodoListService

    @GetMapping("/v1/todo-tasks", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getTodoList(): ResponseEntity<String> = todoListService.getTodoTasks().fold(
        ifLeft = { err -> TodoListError.toResponse(err) },
        ifRight = { todoTasks ->
            ResponseEntity.ok().body(Json.encodeToString(todoTasks))
        }
    )

    @GetMapping("/v1/todo-tasks/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getTodoTaskById(@PathVariable id: String): ResponseEntity<String> = todoListService.getTodoTaskById(id).fold(
        ifLeft = { err -> TodoListError.toResponse(err) },
        ifRight = {
            it.fold(
                ifEmpty = {
                    ResponseEntity.notFound().build()
                },
                ifSome = { todoTask ->
                    ResponseEntity.ok().body(Json.encodeToString(todoTask))
                }
            )
        }
    )


    @PostMapping("/v1/todo-tasks", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createTask(@RequestBody reqBody: TodoTaskReqBody): ResponseEntity<String> = todoListService.createTask(reqBody).fold(
        ifLeft = { err -> TodoListError.toResponse(err) },
        ifRight = { response ->
            ResponseEntity.ok().body(response)
        }
    )
}