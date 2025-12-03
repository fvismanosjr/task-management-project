package com.example.taskmanagementapi.controller

import com.example.taskmanagementapi.dto.LoginRequest
import com.example.taskmanagementapi.dto.LoginResponse
import com.example.taskmanagementapi.dto.RegisterRequest
import com.example.taskmanagementapi.service.AuthService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    fun register(@RequestBody request: RegisterRequest) = authService.registerUser(request)

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(
        @RequestBody request: LoginRequest,
        response: HttpServletResponse
    ): LoginResponse = authService.authenticateUser(request, response)

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun logout(response: HttpServletResponse) = authService.unAuthenticateUser(response)

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    fun me(authentication: Authentication?) = "You are logged in"
}