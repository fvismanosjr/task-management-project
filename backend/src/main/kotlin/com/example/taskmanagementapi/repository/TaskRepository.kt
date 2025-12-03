package com.example.taskmanagementapi.repository

import com.example.taskmanagementapi.entity.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository: JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    fun findAllByBoardId(id: Long): List<Task>
}