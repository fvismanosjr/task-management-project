package com.example.taskmanagementapi.service

import com.example.taskmanagementapi.dto.BoardRequest
import com.example.taskmanagementapi.entity.Board
import com.example.taskmanagementapi.repository.BoardRepository
import org.springframework.stereotype.Service

@Service
class BoardService(
    private val boardRepository: BoardRepository,
) {
    fun findAll() = boardRepository.findAll()

    fun find(id: Long): Board = boardRepository.findById(id).orElseThrow { Exception("board not found") }

    fun save(request: BoardRequest) = boardRepository.save(
        Board(
            name = request.name
        )
    )

    fun edit(id: Long, request: BoardRequest) = boardRepository.save(
        find(id).apply {
            this.name = request.name
        }
    )

    fun destroy(id: Long) = boardRepository.delete(
        find(id)
    )
}