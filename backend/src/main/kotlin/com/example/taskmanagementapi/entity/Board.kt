package com.example.taskmanagementapi.entity

import com.example.taskmanagementapi.dto.BoardMemberResponse
import com.example.taskmanagementapi.dto.BoardResponse
import com.example.taskmanagementapi.dto.BoardResponseWithMembers
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "boards")
class Board(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "name", nullable = false)
    var name: String,

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var members: MutableList<BoardMember> = mutableListOf(),

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var tasks: MutableList<Task> = mutableListOf()
) {
    fun toResponse(): BoardResponse {
        return BoardResponse(
            this.id,
            this.name
        )
    }

    fun toResponseWithMembers(): BoardResponseWithMembers {
        return BoardResponseWithMembers(
            this.id,
            this.name,
            this.members.map {
                BoardMemberResponse(
                    it.id,
                    it.user.username
                )
            }
        )
    }
}