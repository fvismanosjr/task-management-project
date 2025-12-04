package com.example.taskmanagementapi.controller

import com.example.taskmanagementapi.dto.BoardResponse
import com.example.taskmanagementapi.dto.BoardResponseWith
import com.example.taskmanagementapi.dto.BoardResponseWithRelations
import com.example.taskmanagementapi.dto.TaskDto
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller

@Controller
class SocketController(
    private val messagingTemplate: SimpMessagingTemplate
) {
    // Broadcast boards assigned to a user
    fun broadcastUserBoards(userId: Long, boards: List<BoardResponse>) {
        messagingTemplate.convertAndSend("/topic/user.$userId.boards", boards)
    }

    fun broadcastBoard(boardId: Long, board: BoardResponseWith) {
        messagingTemplate.convertAndSend("/topic/board.$boardId", board)
    }

    // Broadcast tasks for a board
    fun broadcastBoardTasks(boardId: Long, tasks: List<TaskDto>) {
        messagingTemplate.convertAndSend("/topic/tasks.$boardId", tasks)
    }

    // Optional: receive updates from client
    @MessageMapping("/board/{boardId}/tasks")
    fun receiveTaskUpdate(boardId: Long, task: TaskDto) {
        broadcastBoardTasks(boardId, listOf(task))
    }
}
