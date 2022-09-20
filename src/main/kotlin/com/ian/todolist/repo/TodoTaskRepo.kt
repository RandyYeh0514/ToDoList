package com.ian.todolist.repo

import arrow.core.Either
import arrow.core.Eval
import arrow.core.Option
import arrow.core.toOption
import com.ian.todolist.model.TodoListError
import com.ian.todolist.model.TodoTask
import com.ian.todolist.model.TodoTaskReqBody
import com.ian.todolist.utils.MongodbResource
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Projections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bson.BsonDocument
import org.bson.BsonObjectId
import org.bson.BsonValue
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TodoTaskRepo{

    @Autowired
    private lateinit var mongodbResource: MongodbResource
    // 被使用到才會載入記憶體
    private val mongodbCollection = Eval.later {
        mongodbResource.getCollection()
    }

    suspend fun insert(payload: TodoTaskReqBody): Either<TodoListError, String> = withContext(Dispatchers.IO) {
        val document = Document.parse(Json.encodeToString(payload))

        mongodbCollection.value.map { collection ->
            collection.insertOne(document)
            document.getObjectId("_id").toString()
        }
    }

    suspend fun findAll(): Either<TodoListError, List<TodoTask>> = withContext(Dispatchers.IO) {
        mongodbCollection.value.map { collection ->
            collection.find()
                .map {
                    val objectId = it.getObjectId("_id")
                    it.append("id", objectId.toString())
                    it.remove("_id")
                    Json.decodeFromString<TodoTask>(it.toJson())
                }.toList()
        }
    }

    suspend fun findById(id: String): Either<TodoListError, Option<TodoTask>> = withContext(Dispatchers.IO) {
        mongodbCollection.value.map { collection ->
            BsonDocument().append("_id", BsonObjectId(ObjectId(id)))
            collection.find(eq("_id", ObjectId(id))).first().toOption().map {
                val objectId = it.getObjectId("_id")
                it.append("id", objectId.toString())
                it.remove("_id")
                Json.decodeFromString(it.toJson())
            }
        }
    }
}