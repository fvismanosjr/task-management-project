package com.example.taskmanagementapi.service

import com.example.taskmanagementapi.dto.BoardRequest
import com.example.taskmanagementapi.dto.BoardResponse
import com.example.taskmanagementapi.dto.BoardResponseWithMembers
import com.example.taskmanagementapi.entity.Board
import com.example.taskmanagementapi.entity.BoardMember
import com.example.taskmanagementapi.repository.BoardMemberRepository
import com.example.taskmanagementapi.repository.BoardRepository
import com.example.taskmanagementapi.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository,
    private val boardMemberRepository: BoardMemberRepository,

) {
    fun findById(
        id: Long
    ): Board = boardRepository
                .findById(id)
                .orElseThrow { Exception("board not found") }

    fun findAll() = boardRepository.findAll().map { it.toResponse() }

    fun find(
        id: Long
    ): BoardResponseWithMembers = boardRepository
                        .findById(id)
                        .map { it.toResponseWithMembers()}
                        .orElseThrow { Exception("board not found") }

    fun save(request: BoardRequest): BoardResponse {

        val board = boardRepository.save(
            Board(
                name = request.name
            )
        )

        val users = userRepository
                    .findByUsernameIn(request.members)
                    .map {
                        BoardMember(board = board, user = it)
                    };

        boardMemberRepository.saveAll(
            users
        )

        return board.toResponse()
    }

    fun edit(id: Long, request: BoardRequest) = boardRepository.save(
        findById(id).apply {
            this.name = request.name
        }
    )

    fun destroy(id: Long) = boardRepository.delete(
        findById(id)
    )
}