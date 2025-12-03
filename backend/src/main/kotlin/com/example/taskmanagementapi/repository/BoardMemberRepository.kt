package com.example.taskmanagementapi.repository

import com.example.taskmanagementapi.entity.BoardMember
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface BoardMemberRepository: JpaRepository<BoardMember, Long>, JpaSpecificationExecutor<BoardMember>