package com.example.taskmanagementapi.dto

import jakarta.validation.constraints.NotBlank

data class BoardRequest(
    @field:NotBlank(message = "Name is required")
    val name: String,
)

data class BoardResponse(
    val id: Long,
    val name: String
)