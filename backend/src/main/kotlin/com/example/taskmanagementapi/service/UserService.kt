package com.example.taskmanagementapi.service

import com.example.taskmanagementapi.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun findAll() = userRepository.findAll().map { it.toResponse() }
}