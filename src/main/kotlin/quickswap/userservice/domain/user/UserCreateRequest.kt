package quickswap.userservice.domain.user

class UserCreateRequest (
  var email: Email,
  var password: Password,
  var address: Address,
  var telephone: Telephone,
)