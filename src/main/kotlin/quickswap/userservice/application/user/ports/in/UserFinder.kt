package quickswap.userservice.application.user.ports.`in`

import quickswap.userservice.domain.user.Email
import quickswap.userservice.domain.user.User
import quickswap.userservice.domain.user.UserId

interface UserFinder {
  fun findByEmail(email: Email): User
  fun findById(id: UserId): User
}