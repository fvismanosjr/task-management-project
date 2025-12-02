package com.example.taskmanagementapi.entity

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

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    var members: MutableList<BoardMember> = mutableListOf(),

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    var tasks: MutableList<Task> = mutableListOf()
)