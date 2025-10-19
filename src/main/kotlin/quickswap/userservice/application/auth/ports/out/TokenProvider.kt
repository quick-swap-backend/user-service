package quickswap.userservice.application.auth.ports.out

import quickswap.userservice.domain.user.Email
import quickswap.userservice.domain.user.UserId

interface TokenProvider {

  fun generateAccessToken(userId: UserId, email: Email): String

  fun generateRefreshToken(userId: UserId): String
}