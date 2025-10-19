package quickswap.userservice.adapter.security

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import quickswap.commons.domain.shared.id.UserId
import quickswap.commons.domain.shared.vo.Email
import quickswap.userservice.application.auth.ports.out.TokenProvider
import java.util.*
import javax.crypto.SecretKey

@Configuration
class JwtTokenProvider(
  @Value("\${jwt.secret}") private val secretKey: String,

  @Value("\${jwt.access-token.validity-second}") private val accessTokenValiditySecond: Long,

  @Value("\${jwt.refresh-token.validity-second}") private val refreshTokenValiditySecond: Long,

  ) : TokenProvider {

  private val key: SecretKey = Keys.hmacShaKeyFor(secretKey.toByteArray())

  companion object {
    const val ACCESS_TYPE = "access"
    const val REFRESH_TYPE = "refresh"
    const val TOKEN_TYPE_CLAIM = "type"
    const val EMAIL_CLAIM = "email"
  }

  override fun generateAccessToken(userId: UserId, email: Email): String {
    val now = Date()
    val expiryDate = Date(now.time + accessTokenValiditySecond * 1000)

    return Jwts.builder()
      .subject(userId.value)
      .claim(EMAIL_CLAIM, email.value)
      .claim(TOKEN_TYPE_CLAIM, ACCESS_TYPE)
      .issuedAt(now)
      .expiration(expiryDate)
      .signWith(key)
      .compact()
  }

  override fun generateRefreshToken(userId: UserId): String {
    val now = Date()
    val expiryDate = Date(now.time + refreshTokenValiditySecond * 1000)

    return Jwts.builder()
      .subject(userId.value)
      .claim(TOKEN_TYPE_CLAIM, REFRESH_TYPE)
      .issuedAt(now)
      .expiration(expiryDate)
      .signWith(key)
      .compact()
  }
}