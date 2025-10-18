package quickswap.userservice.adapter.webapi.dto

import quickswap.userservice.domain.user.User

class UserCreateResponse private constructor(
  val userId: String,
  val email: String
) {

  companion object {
    fun of(user: User): UserCreateResponse {
      return UserCreateResponse(user.id.value, user.email.value)
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as UserCreateResponse

    if (userId != other.userId) return false
    if (email != other.email) return false

    return true
  }

  override fun hashCode(): Int {
    var result = userId.hashCode()
    result = 31 * result + email.hashCode()
    return result
  }

}