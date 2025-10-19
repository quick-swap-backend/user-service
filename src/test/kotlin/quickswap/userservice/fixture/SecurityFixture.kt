package quickswap.userservice.fixture

import quickswap.commons.domain.shared.PasswordEncoder
import quickswap.commons.domain.shared.id.UserId
import quickswap.userservice.adapter.persistence.auth.RefreshTokenEntity
import java.time.LocalDateTime
import java.util.UUID

class SecurityFixture {

  companion object {
    fun getPasswordEncoder(): PasswordEncoder {
      return object : PasswordEncoder {

        override fun encode(password: String): String {
          return "hashed_$password"
        }

        override fun matches(password: String, hashedPassword: String): Boolean {
          return encode(password) == hashedPassword
        }
      }
    }

    fun getRefreshTokenEntity(): RefreshTokenEntity {
      return RefreshTokenEntity.of(
        userId = UserId(UUID.randomUUID().toString()),
        token = "refresh_token_${UUID.randomUUID()}",
        expiryDate = LocalDateTime.MAX
        )
    }
  }
}