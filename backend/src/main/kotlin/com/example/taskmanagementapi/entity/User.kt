package com.example.taskmanagementapi.entity

import com.example.taskmanagementapi.dto.UserResponse
import com.example.taskmanagementapi.dto.UserResponseWith
import com.example.taskmanagementapi.dto.UserResponseWithBoards
import com.example.taskmanagementapi.dto.UserResponseWithBoardsRelations
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "username", nullable = false)
    var username: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "role")
    var role: String,

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    var boardMember: MutableList<BoardMember> = mutableListOf()

) {
    fun toResponse(): UserResponse {
        return UserResponse(
            this.id,
            this.username
        )
    }

    fun toResponseWith(): UserResponseWith {
        return UserResponseWith(
            this.id,
            this.username,
            this.boardMember.map {
                it.board.toResponse()
            }
        )
    }

    fun toResponseWithBoards(): UserResponseWithBoards {
        return UserResponseWithBoards(
            this.id,
            this.username,
            boards = this.boardMember.map {
                it.board.toResponse()
            }
        )
    }

    fun toResponseWithBoardsRelations(): UserResponseWithBoardsRelations {
        return UserResponseWithBoardsRelations(
            this.id,
            this.username,
            boards = this.boardMember.map {
                it.board.toResponseWithRelations()
            }
        )
    }
}