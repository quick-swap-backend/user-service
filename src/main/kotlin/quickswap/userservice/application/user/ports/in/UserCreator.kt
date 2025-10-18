package quickswap.userservice.application.user.ports.`in`

import quickswap.userservice.domain.user.User
import quickswap.userservice.domain.user.UserCreateRequest

interface UserCreator {
  fun create(request: UserCreateRequest): User
}