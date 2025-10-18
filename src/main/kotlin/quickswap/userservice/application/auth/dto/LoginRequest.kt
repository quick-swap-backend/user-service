package quickswap.userservice.application.auth.dto

import quickswap.userservice.domain.user.Email
import quickswap.userservice.domain.user.Password

data class LoginRequest(val email: Email, val password: Password)