package com.example.taskmanagementapi.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SocketService(
//    private val boardRepository: BoardRepository,
//    private val taskRepository: TaskRepository,
//    private val userRepository: UserRepository,
//    private val socketController: BoardTaskSocketController
) {

//    @Transactional
//    fun addUserToBoard(userId: Long, boardId: Long) {
//        val user = userRepository.findById(userId).orElseThrow()
//        val board = boardRepository.findById(boardId).orElseThrow()
//
//        board.members.add(user)
//        boardRepository.save(board)
//
//        // Notify user of updated boards
//        val userBoards = boardRepository.findAllByMembersContaining(user)
//            .map { BoardDto(it.id, it.name) }
//
//        socketController.broadcastUserBoards(userId, userBoards)
//    }
//
//    @Transactional
//    fun removeUserFromBoard(userId: Long, boardId: Long) {
//        val user = userRepository.findById(userId).orElseThrow()
//        val board = boardRepository.findById(boardId).orElseThrow()
//
//        board.members.remove(user)
//        boardRepository.save(board)
//
//        val userBoards = boardRepository.findAllByMembersContaining(user)
//            .map { BoardDto(it.id, it.name) }
//
//        socketController.broadcastUserBoards(userId, userBoards)
//    }
//
//    @Transactional
//    fun addTask(boardId: Long, title: String) {
//        val board = boardRepository.findById(boardId).orElseThrow()
//        val task = Task(title = title, board = board)
//        taskRepository.save(task)
//
//        val tasks = taskRepository.findAllByBoard(board).map { TaskDto(it.id, it.title, it.board.id) }
//        socketController.broadcastBoardTasks(boardId, tasks)
//    }
//
//    @Transactional
//    fun updateTask(taskId: Long, title: String) {
//        val task = taskRepository.findById(taskId).orElseThrow()
//        task.title = title
//        taskRepository.save(task)
//
//        val tasks = taskRepository.findAllByBoard(task.board)
//            .map { TaskDto(it.id, it.title, it.board.id) }
//
//        socketController.broadcastBoardTasks(task.board.id, tasks)
//    }
//
//    @Transactional
//    fun removeTask(taskId: Long) {
//        val task = taskRepository.findById(taskId).orElseThrow()
//        val boardId = task.board.id
//        taskRepository.delete(task)
//
//        val tasks = taskRepository.findAllByBoard(task.board)
//            .map { TaskDto(it.id, it.title, it.board.id) }
//
//        socketController.broadcastBoardTasks(boardId, tasks)
//    }
}
