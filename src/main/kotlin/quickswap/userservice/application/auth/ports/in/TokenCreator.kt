package quickswap.userservice.application.auth.ports.`in`

import quickswap.userservice.domain.user.Email
import quickswap.userservice.domain.user.Password

interface TokenCreator {
  fun createToken(email: Email, password: Password): Pair<String, String>

  /* refresh 토큰을 통해 엑세스 토큰을 재발급합니다. */
  fun accessTokenRefresh(refreshToken: String): String
}