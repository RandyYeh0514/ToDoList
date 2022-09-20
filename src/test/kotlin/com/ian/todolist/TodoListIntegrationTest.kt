package com.ian.todolist

import com.ian.todolist.config.MongodbConfig
import com.ian.todolist.utils.MongodbTestContainers
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [TodolistApplication::class])
//@EnableAutoConfiguration(exclude = [MongodbConfig::class])
class TodoListIntegrationTest {

    @LocalServerPort
    var port: Int = 0

    companion object {
        @BeforeAll
        @JvmStatic
        fun setUp() {
            MongodbTestContainers.start()
        }

        @DynamicPropertySource
        @JvmStatic
        fun registerProperties(registry: DynamicPropertyRegistry) {
            registry.add("mongodb.host") {
                "mongodb://localhost"
            }
            registry.add("mongodb.port") {
                MongodbTestContainers.mongodbContainers.getMappedPort(27017)
            }
        }
        @AfterAll
        @JvmStatic
        fun complete() {
            MongodbTestContainers.stop()
        }
    }
}