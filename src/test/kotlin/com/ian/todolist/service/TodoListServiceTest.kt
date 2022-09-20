package com.ian.todolist.service

import arrow.core.toOption
import com.ian.todolist.TodoListIntegrationTest
import com.ian.todolist.model.Context
import com.ian.todolist.model.Title
import com.ian.todolist.model.TodoTask
import com.ian.todolist.model.TodoTaskReqBody
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

class TodoListServiceTest: TodoListIntegrationTest() {

    @Autowired
    private lateinit var todoListService: TodoListService

    @Test
    fun testGetTodoTasks() {
        runBlocking {
            val payload: TodoTaskReqBody = TodoTaskReqBody(
                title = Title("xxxx"),
                context = Context("xxxxxxxxx")
            )
            todoListService.createTask(payload)

            val restTemplate = RestTemplate()
            val response = restTemplate.exchange<String>("http://localhost:$port/api/v1/todo-tasks", HttpMethod.GET,null , String::class)
            response.body.toOption().fold(
                ifEmpty = { Assertions.fail("response is null") },
                ifSome = {
                    val todoTasks: List<TodoTask> = Json.decodeFromString(it)

                    Assertions.assertEquals(1, todoTasks.size)
                    Assertions.assertEquals("xxxx", todoTasks[0].title.value)
                    Assertions.assertEquals("xxxxxxxxx", todoTasks[0].context.value)
                }
            )
        }
    }
}