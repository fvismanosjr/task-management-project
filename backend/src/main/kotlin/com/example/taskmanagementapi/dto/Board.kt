package com.example.taskmanagementapi.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class BoardRequest(
    @field:NotBlank(message = "Name is required")
    val name: String,

    @field:Size(min = 1)
    val members: List<String>
)

data class BoardResponse(
    val id: Long,
    val name: String
)

data class BoardResponseWith(
    val id: Long,
    val name: String,
    val members: List<BoardMemberResponse>,
    val tasks: List<TaskResponse>
)

data class BoardResponseWithMembers(
    val id: Long,
    val name: String,
    val members: List<BoardMemberResponse>
)

data class BoardResponseWithRelations(
    val id: Long,
    val name: String,
    val members: List<BoardMemberResponse>,
    val tasks: List<TaskDto>
)