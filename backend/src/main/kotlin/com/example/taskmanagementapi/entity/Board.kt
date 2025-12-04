package com.example.taskmanagementapi.entity

import com.example.taskmanagementapi.dto.BoardResponse
import com.example.taskmanagementapi.dto.BoardResponseWith
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

    @OneToMany(
        mappedBy = "board",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var members: MutableList<BoardMember> = mutableListOf(),

    @OneToMany(
        mappedBy = "board",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var tasks: MutableList<Task> = mutableListOf()
) {
    fun toResponse(): BoardResponse {
        return BoardResponse(
            this.id,
            this.name
        )
    }

    fun toResponseWith(): BoardResponseWith {
        return BoardResponseWith(
            this.id,
            this.name,
            members = this.members.map { it.toResponse() },
            tasks = this.tasks.map { it.toResponse() }
        )
    }
}