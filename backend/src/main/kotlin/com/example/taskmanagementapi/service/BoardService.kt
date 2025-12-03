package com.example.taskmanagementapi.service

import com.example.taskmanagementapi.controller.SocketController
import com.example.taskmanagementapi.dto.BoardRequest
import com.example.taskmanagementapi.dto.BoardResponse
import com.example.taskmanagementapi.dto.BoardResponseWithMembers
import com.example.taskmanagementapi.dto.TaskDto
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
    private val socketController: SocketController,
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

        val boardMembers = userRepository
                            .findByUsernameIn(request.members)
                            .map {
                                BoardMember(board = board, user = it)
                            }

        boardMemberRepository.saveAll(
            boardMembers
        )

        // broadcast
        boardMembers.forEach { member ->
            val boardByUser = userRepository
                            .findById(member.user.id)
                            .map { it.toResponseWithBoards() }
                            .orElseThrow { Exception("not found") }
            
            println("userId: ${member.user.id}")
            socketController.broadcastUserBoards(member.user.id, boardByUser.boards)
        }

        return board.toResponse()
    }

    fun edit(id: Long, request: BoardRequest): BoardResponse {

        val board = boardRepository.findById(id).orElseThrow()
        val users = userRepository.findByUsernameIn(request.members)

        val currentUsers = board.members.map { it.user }
        val toRemove = board.members.filter { it.user !in users }
        board.members.removeAll(toRemove)

        val existingUserIds = currentUsers.map { it.id }.toSet()
        val toAdd = users.filter { it.id !in existingUserIds }

        toAdd.forEach { user ->
            board.members.add(BoardMember(board = board, user = user))
        }

        board.name = request.name
        boardRepository.save(board)

        // broadcast
        users.forEach { user ->
            val boardByUser = userRepository
                                .findById(user.id)
                                .map { it.toResponseWithBoards() }
                                .orElseThrow { Exception("not found") }

            println("userId: ${user.id}")
            socketController.broadcastUserBoards(user.id, boardByUser.boards)
        }

        return board.toResponse()
    }

    fun destroy(id: Long) {
        val board = findById(id)
        val members = board.members

        boardRepository.delete(
            findById(id)
        )

        // broadcast
        members.forEach { member ->
            val boardByUser = userRepository
                                .findById(member.user.id)
                                .map { it.toResponseWithBoards() }
                                .orElseThrow { Exception("not found") }

            println("userId: ${member.user.id}")
            socketController.broadcastUserBoards(member.user.id, boardByUser.boards)
        }
    }

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

        val board = findById(id)
        val task = taskRepository.save(
            Task(
                board = board,
                title = request.title,
                assignee = findMemberById(request.assignee),
                comment = request.comment
            )
        )

        // broadcast
        println("broadcasting save task")
        socketController.broadcastBoardTasks(id, board.tasks.map {
            TaskDto(
                it.id,
                it.title
            )
        })

        return task.toResponse()
    }

    fun updateTask(
        id: Long,
        taskId: Long,
        request: TaskRequest
    ): TaskResponse {
        val board = findById(id)
        val task =  taskRepository.save(
            findTaskById(taskId).apply {
                this.board = board
                this.title = request.title
                this.assignee = findMemberById(request.assignee)
                this.comment = request.comment
            }
        )

        // broadcast
        println("broadcasting edit task")
        socketController.broadcastBoardTasks(id, board.tasks.map {
            TaskDto(
                it.id,
                it.title
            )
        })

        return task.toResponse()
    }

    fun destroyTask(id: Long, taskId: Long) {
        val board = findById(id)

        taskRepository.delete(findTaskById(taskId))

        // broadcast
        println("broadcasting delete task")
        socketController.broadcastBoardTasks(id, board.tasks.map {
            TaskDto(
                it.id,
                it.title
            )
        })
    }
}