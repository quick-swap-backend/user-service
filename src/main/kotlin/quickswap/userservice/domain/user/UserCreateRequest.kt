package quickswap.userservice.domain.user

import quickswap.commons.domain.shared.vo.Address
import quickswap.commons.domain.shared.vo.Email
import quickswap.commons.domain.shared.vo.Password
import quickswap.commons.domain.shared.vo.Telephone

data class UserCreateRequest (
  val email: Email,
  val password: Password,
  val address: Address,
  val telephone: Telephone,
)