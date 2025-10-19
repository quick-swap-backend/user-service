package quickswap.userservice.application.user.ports.`in`

import quickswap.commons.domain.shared.id.UserId
import quickswap.commons.domain.shared.vo.Email
import quickswap.userservice.domain.user.User

interface UserFinder {
  fun findByEmail(email: Email): User
  fun findById(id: UserId): User
}