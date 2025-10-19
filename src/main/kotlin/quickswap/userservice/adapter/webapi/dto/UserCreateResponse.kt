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
}