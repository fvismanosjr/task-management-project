package com.example.taskmanagementapi.controller

import com.example.taskmanagementapi.dto.BoardRequest
import com.example.taskmanagementapi.dto.TaskRequest
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

    @GetMapping("/{id}/tasks")
    @ResponseStatus(HttpStatus.OK)
    fun findAllTasks(
        @PathVariable id: Long,
    ) = boardService.findAllTasks(id)

    @GetMapping("/{id}/tasks/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    fun findTask(
        @PathVariable id: Long,
        @PathVariable taskId: Long,
    ) = boardService.findTask(taskId)

    @PostMapping("/{id}/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    fun taskStore(
        @PathVariable id: Long,
        @Valid @RequestBody request: TaskRequest
    ) = boardService.saveTask(id, request)

    @PutMapping("/{id}/tasks/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    fun taskUpdate(
        @PathVariable id: Long,
        @PathVariable taskId: Long,
        @Valid @RequestBody request: TaskRequest
    ) = boardService.updateTask(id, taskId, request)

    @DeleteMapping("/{id}/tasks/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun taskDestroy(
        @PathVariable id: Long,
        @PathVariable taskId: Long,
    ) = boardService.destroyTask(id, taskId)


}