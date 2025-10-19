package quickswap.userservice.adapter.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import quickswap.userservice.application.auth.ports.out.TokenResolver
import quickswap.userservice.domain.user.Email
import quickswap.userservice.domain.user.UserId
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import javax.crypto.SecretKey

@Configuration
class JwtTokenResolver(
  @Value("\${jwt.secret}")
  private val secretKey: String,

  ) : TokenResolver {

  private val key: SecretKey = Keys.hmacShaKeyFor(secretKey.toByteArray())

  companion object {
    const val TOKEN_TYPE_CLAIM = "type"
    const val EMAIL_CLAIM = "email"
  }

  override fun getUserIdFromToken(token: String): UserId {
    return UserId(getClaims(token).subject)
  }

  override fun getEmailFromToken(token: String): Email {
    return Email(getClaims(token).get(EMAIL_CLAIM, String::class.java))
  }

  override fun validateToken(token: String): Boolean {
    return try {
      getClaims(token)
      true
    } catch (_: ExpiredJwtException) {
      throw TokenExpiredException("토큰이 만료되었습니다")
    } catch (_: UnsupportedJwtException) {
      throw InvalidTokenException("지원하지 않는 토큰입니다")
    } catch (_: MalformedJwtException) {
      throw InvalidTokenException("잘못된 형식의 토큰입니다")
    } catch (_: SignatureException) {
      throw InvalidTokenException("서명이 유효하지 않습니다")
    } catch (_: IllegalArgumentException) {
      throw InvalidTokenException("토큰이 비어있습니다")
    }
  }

  override fun getTokenType(token: String): String {
    return getClaims(token).get(TOKEN_TYPE_CLAIM, String::class.java)
  }

  private fun getClaims(token: String): Claims {
    return Jwts.parser()
      .verifyWith(key)
      .build()
      .parseSignedClaims(token)
      .payload
  }

  override fun getExpirationDate(token: String): Date {
    return getClaims(token).expiration
  }

  override fun getExpirationDateTime(token: String): LocalDateTime {
    return getExpirationDate(token)
      .toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDateTime()
  }

  override fun isTokenExpired(token: String): Boolean {
    return try {
      getExpirationDateTime(token).isBefore(
        Date().toInstant()
          .atZone(ZoneId.systemDefault())
          .toLocalDateTime()
      )
    } catch (_: ExpiredJwtException) {
      true
    }
  }
}