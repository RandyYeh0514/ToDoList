package com.ian.todolist

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [MongoAutoConfiguration::class])
class TodolistApplication

fun main(args: Array<String>) {
	runApplication<TodolistApplication>(*args)
}
