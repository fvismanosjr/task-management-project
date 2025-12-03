package com.example.taskmanagementapi.service

import com.example.taskmanagementapi.dto.BoardRequest
import com.example.taskmanagementapi.dto.BoardResponse
import com.example.taskmanagementapi.dto.BoardResponseWithMembers
import com.example.taskmanagementapi.dto.TaskRequest
import com.example.taskmanagementapi.dto.TaskResponse
import com.example.taskmanagementapi.entity.Board
import com.example.taskmanagementapi.entity.BoardMember
import com.example.taskmanagementapi.entity.Task
import com.example.taskmanagementapi.repository.BoardMemberRepository
import com.example.taskmanagementapi.repository.BoardRepository
import com.example.taskmanagementapi.repository.TaskRepository
import com.example.taskmanagementapi.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val boardMemberRepository: BoardMemberRepository,
    private val userRepository: UserRepository,
    private val taskRepository: TaskRepository,

) {
    fun findTaskById(
        id: Long
    ): Task = taskRepository
        .findById(id)
        .orElseThrow { Exception("not found") }

    fun findMemberById(
        id: Long
    ): BoardMember = boardMemberRepository
                        .findById(id)
                        .orElseThrow { Exception("not found") }

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

    fun findAllTasks(
        id: Long
    ) = taskRepository.findAllByBoardId(id).map { it.toResponse() }

    fun findTask(
        id: Long
    ): TaskResponse = taskRepository
                        .findById(id)
                        .map { it.toResponse() }
                        .orElseThrow { Exception("task not found") }

    fun saveTask(
        id: Long,
        request: TaskRequest
    ): TaskResponse {
        return taskRepository.save(
            Task(
                board = findById(id),
                title = request.title,
                assignee = findMemberById(request.assignee),
                comment = request.comment
            )
        ).toResponse()
    }

    fun updateTask(
        id: Long,
        taskId: Long,
        request: TaskRequest
    ): TaskResponse {
        return taskRepository.save(
            findTaskById(taskId).apply {
                board = findById(id)
                title = request.title
                assignee = findMemberById(request.assignee)
                comment = request.comment
            }
        ).toResponse()
    }

    fun destroyTask(id: Long) = taskRepository.delete(
        findTaskById(id)
    )
}