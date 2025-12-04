package com.example.taskmanagementapi.service

import com.example.taskmanagementapi.controller.SocketController
import com.example.taskmanagementapi.dto.BoardRequest
import com.example.taskmanagementapi.dto.BoardResponse
import com.example.taskmanagementapi.dto.BoardResponseWith
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

    fun findBoardById(
        id: Long
    ): Board = boardRepository
                .findById(id)
                .orElseThrow { Exception("board not found") }

    fun findAllBoards() = boardRepository.findAll().map { it.toResponse() }

    fun find(
        id: Long
    ): BoardResponseWith = boardRepository
                        .findById(id)
                        .map { it.toResponseWith() }
                        .orElseThrow { Exception("board not found") }

    fun save(
        request: BoardRequest
    ): BoardResponse {

        val board = boardRepository.save(
            Board(
                name = request.name
            )
        )

        val users = userRepository.findByUsernameIn(request.members)

        boardMemberRepository.saveAll(
            users.map { BoardMember(board = board, user = it) }
        )

        // broadcast
        users.map { it.toResponseWith() }.forEach {
            println("broadcasting to user: ${it.id}")
            socketController.broadcastUserBoards(it.id, it.boards)
        }

        return board.toResponse()
    }

    fun edit(
        id: Long,
        request: BoardRequest
    ): BoardResponse {

        val board = findBoardById(id)
        val newUsers = userRepository.findByUsernameIn(request.members) // new users

        // remove all current members(user) that was not part of this new users
        val toRemove = board.members.filter { it.user !in newUsers }
            board.members.removeAll(toRemove)

        val currentUserIds = board.members.map { it.user.id } // current users
        val toAdd = newUsers
                    .filter { it.id !in currentUserIds }
                    .map { BoardMember(board = board, user = it) }

            board.members.addAll(toAdd)

        board.name = request.name
        boardRepository.save(board)

        // broadcast to new users
        newUsers.map { it.toResponseWith() }.forEach {
            println("broadcasting to user: ${it.id}")
            socketController.broadcastUserBoards(it.id, it.boards)
        }

        // broadcast to old users
        toRemove.forEach { member ->
            val user = member.user.toResponseWith()

            println("broadcasting to user: ${user.id}")
            socketController.broadcastUserBoards(user.id, user.boards)
        }

        // broadcast to board
        println("broadcasting board")
        socketController.broadcastBoard(id, board.toResponseWith())

        return board.toResponse()
    }

    fun destroy(id: Long) {
        val board = findBoardById(id)
        val members = board.members

        boardRepository.delete(
            findBoardById(id)
        )

        // broadcast
        members.forEach { member ->
            val user = userRepository
                        .findById(member.user.id)
                        .map { it.toResponseWith() }
                        .orElseThrow { Exception("not found") }

            println("broadcasting to user: ${member.user.id}")
            socketController.broadcastUserBoards(member.user.id, user.boards)
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

        val board = findBoardById(id)
        val task = taskRepository.save(
            Task(
                board = board,
                title = request.title,
                assignee = findMemberById(request.assignee),
                comment = request.comment
            )
        )

        // broadcast
        println("broadcasting board")
        socketController.broadcastBoard(id, board.toResponseWith())

        return task.toResponse()
    }

    fun updateTask(
        id: Long,
        taskId: Long,
        request: TaskRequest
    ): TaskResponse {
        val board = findBoardById(id)
        val task =  taskRepository.save(
            findTaskById(taskId).apply {
                this.board = board
                this.title = request.title
                this.assignee = findMemberById(request.assignee)
                this.comment = request.comment
            }
        )

        // broadcast
        println("broadcasting board")
        socketController.broadcastBoard(id, board.toResponseWith())

        return task.toResponse()
    }

    fun destroyTask(id: Long, taskId: Long) {
        val board = findBoardById(id)

        taskRepository.delete(findTaskById(taskId))

        // broadcast
        println("broadcasting board")
        socketController.broadcastBoard(id, board.toResponseWith())
    }
}