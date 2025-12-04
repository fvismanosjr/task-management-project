package com.example.taskmanagementapi.controller

import com.example.taskmanagementapi.dto.BoardResponseWith
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller

@Controller
class SocketController(
    private val messagingTemplate: SimpMessagingTemplate
) {
    // Broadcast boards assigned to a user
    fun broadcastUserBoards(userId: Long, boards: List<BoardResponseWith>) {
        messagingTemplate.convertAndSend("/topic/user.$userId.boards", boards)
    }

    fun broadcastBoard(boardId: Long, board: BoardResponseWith) {
        messagingTemplate.convertAndSend("/topic/board.$boardId", board)
    }
}
