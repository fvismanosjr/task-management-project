package com.example.taskmanagementapi.service

import com.example.taskmanagementapi.dto.UserResponseWithBoards
import com.example.taskmanagementapi.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun findAll() = userRepository.findAll().map { it.toResponse() }

    fun find(
        id: Long
    ): UserResponseWithBoards = userRepository
        .findById(id)
        .map { it.toResponseWithBoards() }
        .orElseThrow { Exception("not found") }
}