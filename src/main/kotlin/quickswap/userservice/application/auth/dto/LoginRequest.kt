package quickswap.userservice.application.auth.dto

import quickswap.commons.domain.shared.vo.Email
import quickswap.commons.domain.shared.vo.Password


data class LoginRequest(val email: Email, val password: Password)