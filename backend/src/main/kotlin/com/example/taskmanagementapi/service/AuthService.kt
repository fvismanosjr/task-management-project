package com.example.taskmanagementapi.service

import com.example.taskmanagementapi.dto.LoginRequest
import com.example.taskmanagementapi.dto.LoginResponse
import com.example.taskmanagementapi.dto.RegisterRequest
import com.example.taskmanagementapi.entity.User
import com.example.taskmanagementapi.repository.UserRepository
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) {
    fun registerUser(request: RegisterRequest) : String {
        userRepository
            .findByUsername(request.username.lowercase())
            ?.let { throw Exception("user already registered") }

        val user = userRepository.save(
            User(
                username = request.username.lowercase(),
                password = BCryptPasswordEncoder().encode(request.password),
                role = "ROLE_USER"
            )
        )

        return "User registered successfully"
    }

    fun authenticateUser(request: LoginRequest, response: HttpServletResponse) : LoginResponse {
        val user = request
            .let { userRepository.findByUsername(it.username) ?: throw Exception("user not found") }
            .takeIf { BCryptPasswordEncoder().matches(request.password, it.password) }
            ?: throw BadCredentialsException("Invalid credentials")

        // process cookie
        response.addCookie(jwtService.generateCookie(
            token = jwtService.generateToken(user.username)
        ))

        return LoginResponse(
            id = user.id,
            username = user.username,
            role = user.role,
        )
    }

    fun unAuthenticateUser(response: HttpServletResponse)
    {
        jwtService.clearCookie(response)
    }
}