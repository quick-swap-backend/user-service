package quickswap.userservice.application.auth.ports.`in`

import quickswap.commons.domain.shared.vo.Email
import quickswap.commons.domain.shared.vo.Password


interface TokenCreator {
  fun createToken(email: Email, password: Password): Pair<String, String>

  /* refresh 토큰을 통해 엑세스 토큰을 재발급합니다. */
  fun accessTokenRefresh(refreshToken: String): String
}