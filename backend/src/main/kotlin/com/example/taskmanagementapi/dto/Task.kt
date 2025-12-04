package com.example.taskmanagementapi.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class TaskRequest(
    @field:NotBlank(message = "Title is required")
    val title: String,

    @field:Min(1)
    val assignee: Long,

    val comment: String?
)

data class TaskResponse(
    val id: Long,
    val title: String,
    val comment: String?,
    val assignee: BoardMemberResponse,
)