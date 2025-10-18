package quickswap.userservice.domain.user

data class UserCreateRequest (
  val email: Email,
  val password: Password,
  val address: Address,
  val telephone: Telephone,
)