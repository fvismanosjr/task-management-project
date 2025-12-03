package com.example.taskmanagementapi.service

import com.example.taskmanagementapi.repository.BoardMemberRepository
import org.springframework.stereotype.Service

@Service
class BoardMemberService(
    private val boardMemberRepository: BoardMemberRepository,
) {
    fun findAll() = boardMemberRepository.findAll()
}