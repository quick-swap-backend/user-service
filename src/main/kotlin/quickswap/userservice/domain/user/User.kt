package quickswap.userservice.domain.user

import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import quickswap.userservice.domain.shared.PasswordEncoder

@Entity
class User private constructor(

  @EmbeddedId
  val id: UserId,

  @Embedded
  var email: Email,

  var hashedPassword: String,

  @Embedded
  var address: Address,

  @Embedded
  var telephone: Telephone,

  /* 평판 평균 점수. 1~5 */
  @Embedded
  var reputation: Reputation,

) {

  fun verifyPassword(password:Password, passwordEncoder: PasswordEncoder): Boolean {
    return passwordEncoder.matches(password.value, hashedPassword)
  }

  fun changePassword(newPassword:Password, passwordEncoder: PasswordEncoder) {
    hashedPassword = passwordEncoder.encode(newPassword.value)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as User

    return id == other.id
  }

  override fun hashCode(): Int {
    return id.hashCode()
  }

  companion object {

    fun of(
      id: UserId,
      email: Email,
      password: Password,
      passwordEncoder: PasswordEncoder,
      address: Address,
      telephone: Telephone,
      reputation: Reputation = Reputation(null)
    ): User {
      val hashedPassword = passwordEncoder.encode(password.value)
      require(hashedPassword.isNotBlank()) {
        "비밀번호 해싱 실패"
      }

      return User(
        id = id,
        email = email,
        hashedPassword = hashedPassword,
        address = address,
        telephone = telephone,
        reputation = reputation
      )
    }
  }
}
