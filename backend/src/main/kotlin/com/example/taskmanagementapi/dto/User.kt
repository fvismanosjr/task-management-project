package com.example.taskmanagementapi.dto

import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.NotBlank

data class RegisterRequest(
    @field:NotBlank(message = "Username is required")
    val username: String,

    @field:NotBlank(message = "Password is required")
    val password: String,

    val confirmPassword: String,
) {
    @AssertTrue(message = "Passwords do not match")
    fun isPasswordMatch(): Boolean
    {
        return password == confirmPassword
    }
}

data class LoginRequest(
    @field:NotBlank(message = "Username is required")
    val username: String,

    @field:NotBlank(message = "Password is required")
    val password: String,
)

data class LoginResponse(
    val id: Long,
    val username: String,
    val role: String,
)