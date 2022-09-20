package com.ian.todolist.service

import arrow.core.Either
import arrow.core.Option
import com.ian.todolist.model.TodoListError
import com.ian.todolist.model.TodoTask
import com.ian.todolist.model.TodoTaskReqBody
import com.ian.todolist.repo.TodoTaskRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TodoListService {

    @Autowired
    private lateinit var todoTaskRepo: TodoTaskRepo

    suspend fun createTask(todoTask: TodoTaskReqBody): Either<TodoListError, String> {
        return todoTaskRepo.insert(todoTask)
    }

    suspend fun getTodoTasks(): Either<TodoListError, List<TodoTask>> {
        return todoTaskRepo.findAll()
    }

    suspend fun getTodoTaskById(id: String): Either<TodoListError, Option<TodoTask>> {
        return todoTaskRepo.findById(id)
    }
}