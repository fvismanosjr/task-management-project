package com.example.taskmanagementapi.repository

import com.example.taskmanagementapi.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    fun findByUsername(username: String): User?
}