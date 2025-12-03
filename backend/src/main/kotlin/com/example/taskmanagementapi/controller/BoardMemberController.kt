package com.example.taskmanagementapi.controller

import com.example.taskmanagementapi.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/board-members")
class BoardMemberController(
    private val userService: UserService,
) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll() = userService.findAll()
}