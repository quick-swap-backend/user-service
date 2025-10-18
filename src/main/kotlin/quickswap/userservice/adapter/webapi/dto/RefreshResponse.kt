package quickswap.userservice.adapter.webapi.dto

data class RefreshResponse(val accessToken: String) {
  companion object {
    fun of(accessToken: String): RefreshResponse {
      return RefreshResponse(accessToken)
    }
  }
}