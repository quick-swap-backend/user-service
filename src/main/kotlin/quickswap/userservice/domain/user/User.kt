package quickswap.userservice.domain.user

import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import quickswap.commons.domain.shared.PasswordEncoder
import quickswap.commons.domain.shared.id.UserId
import quickswap.commons.domain.shared.vo.Address
import quickswap.commons.domain.shared.vo.Email
import quickswap.commons.domain.shared.vo.Password
import quickswap.commons.domain.shared.vo.Telephone

@Entity
@Table(
  name = "users",
  uniqueConstraints = [
    UniqueConstraint(name = "uk_user_email", columnNames = ["email"])
  ]
)
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

  fun verifyPassword(password: Password, passwordEncoder: PasswordEncoder): Boolean {
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
      userCreateRequest: UserCreateRequest,
      passwordEncoder: PasswordEncoder,
      reputation: Reputation = Reputation(null)
    ): User {
      val hashedPassword = passwordEncoder.encode(userCreateRequest.password.value)

      require(hashedPassword.isNotBlank()) {
        "비밀번호 해싱 실패"
      }

      return User(
        id = id,
        email = userCreateRequest.email,
        hashedPassword = hashedPassword,
        address = userCreateRequest.address,
        telephone = userCreateRequest.telephone,
        reputation = reputation
      )
    }
  }
}
