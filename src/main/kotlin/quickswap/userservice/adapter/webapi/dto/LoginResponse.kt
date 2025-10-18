package quickswap.userservice.adapter.webapi.dto

data class LoginResponse(
  val accessToken: String,
  val refreshToken: String,
) {
  companion object {
    fun of(accessToken:String, refreshToken:String): LoginResponse{
      return LoginResponse(accessToken, refreshToken)
    }
  }
}
