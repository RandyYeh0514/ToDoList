package com.ian.todolist.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank

@Validated
@Configuration
@ConfigurationProperties(prefix = "mongodb")
class MongodbConfig {
    @NotBlank
    lateinit var host: String

    @NotBlank
    lateinit var port: String
}