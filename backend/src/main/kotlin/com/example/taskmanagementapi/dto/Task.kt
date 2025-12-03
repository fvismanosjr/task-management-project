package com.example.taskmanagementapi.dto

import jakarta.validation.constraints.NotBlank

data class TaskRequest(
    @field:NotBlank(message = "Title is required")
    val title: String,

    @field:NotBlank(message = "Assignee is required")
    val assignee: String,

    val comment: String?
)

data class TaskResponse(
    val id: Long,
    val title: String
)