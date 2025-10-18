package quickswap.userservice.adapter.persistence.auth

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import quickswap.userservice.domain.user.UserId
import java.time.LocalDateTime

@Entity
@Table(name = "refresh_tokens")
class RefreshTokenEntity private constructor(

  @Id @GeneratedValue
  val id: Long? = null,

  val userId: String,

  @Column(length = 1023)
  val token: String,

  val expiryDate: LocalDateTime,

  var isForceExpired: Boolean = false,
) {

  fun forceExpiry() {
    isForceExpired = true
  }

  fun isExpired(): Boolean {
    return isForceExpired || expiryDate.isBefore(LocalDateTime.now())
  }

  fun isActive(): Boolean = !isExpired()

  companion object {
    fun of(userId: UserId, token: String, expiryDate: LocalDateTime): RefreshTokenEntity {
      return RefreshTokenEntity(
        userId = userId.value,
        token = token,
        expiryDate = expiryDate,
      )
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as RefreshTokenEntity

    return id == other.id
  }

  override fun hashCode(): Int {
    return id.hashCode()
  }
}