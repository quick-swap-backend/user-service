package quickswap.userservice.application.auth.ports.out

import quickswap.userservice.domain.user.Email
import quickswap.userservice.domain.user.UserId
import java.time.LocalDateTime
import java.util.Date

interface TokenProvider {

  fun generateAccessToken(userId: UserId, email: Email): String

  fun generateRefreshToken(userId: UserId): String

  fun getUserIdFromToken(token: String): UserId

  fun getEmailFromToken(token: String): Email

  fun validateToken(token: String): Boolean

  fun getTokenType(token: String): String

  fun getExpirationDate(token: String): Date

  fun getExpirationDateTime(token: String): LocalDateTime

  fun isTokenExpired(token: String): Boolean
}