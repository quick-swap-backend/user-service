package quickswap.userservice.application.auth.ports.out

import quickswap.commons.domain.shared.id.UserId
import quickswap.commons.domain.shared.vo.Email


interface TokenProvider {

  fun generateAccessToken(userId: UserId, email: Email): String

  fun generateRefreshToken(userId: UserId): String
}