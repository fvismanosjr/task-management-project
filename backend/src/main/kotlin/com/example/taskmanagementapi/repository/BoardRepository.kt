package com.example.taskmanagementapi.repository

import com.example.taskmanagementapi.entity.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository: JpaRepository<Board, Long>, JpaSpecificationExecutor<Board>