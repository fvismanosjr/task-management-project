package com.example.taskmanagementapi.controller

import com.example.taskmanagementapi.dto.BoardRequest
import com.example.taskmanagementapi.service.BoardService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/boards")
class BoardController(
    private val boardService: BoardService
) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll() = boardService.findAll()

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun find(
        @PathVariable id: Long
    ) = boardService.find(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun store(
        @Valid @RequestBody request: BoardRequest
    ) = boardService.save(request)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody request: BoardRequest
    ) = boardService.edit(id, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun destroy(@PathVariable id: Long) = boardService.destroy(id)
}