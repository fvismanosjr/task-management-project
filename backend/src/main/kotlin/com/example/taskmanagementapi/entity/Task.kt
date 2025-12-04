package com.example.taskmanagementapi.entity

import com.example.taskmanagementapi.dto.TaskResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tasks")
class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    var board: Board,

    @Column(name = "title")
    var title: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_member_id", nullable = false)
    var assignee: BoardMember,

    @Column(name = "comment", columnDefinition = "TEXT")
    var comment: String?,
) {
    fun toResponse(): TaskResponse {
        return TaskResponse(
            this.id,
            this.title,
            this.comment,
            assignee = this.assignee.toResponse()
        )
    }
}