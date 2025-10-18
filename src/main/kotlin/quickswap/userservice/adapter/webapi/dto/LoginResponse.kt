package quickswap.userservice.adapter.webapi.dto

data class LoginResponse(
  val accessToken: String,
  val refreshToken: String,
)