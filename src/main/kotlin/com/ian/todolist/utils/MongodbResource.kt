package com.ian.todolist.utils

import arrow.core.Either
import arrow.core.Eval
import arrow.core.flatMap
import com.ian.todolist.config.MongodbConfig
import com.ian.todolist.model.TodoListError
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MongodbResource {

    @Autowired
    private lateinit var mongodbConfig: MongodbConfig

    fun getCollection(): Either<TodoListError, MongoCollection<Document>> = getDatabase().value().map { database ->
        database.getCollection("todo-list")
    }

    private fun getDatabase() = Eval.later {
        Either.catch {
            val mongodbClient: MongoClient = MongoClients.create("${mongodbConfig.host}:${mongodbConfig.port}")
            mongodbClient.getDatabase("EvaluationProject")
        }.mapLeft {
            TodoListError.DatabaseError(it)
        }
    }
}