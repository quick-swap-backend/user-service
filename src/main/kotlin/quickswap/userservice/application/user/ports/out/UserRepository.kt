package quickswap.userservice.application.user.ports.out

import org.springframework.data.jpa.repository.JpaRepository
import quickswap.userservice.domain.user.Email
import quickswap.userservice.domain.user.User
import quickswap.userservice.domain.user.UserId

interface UserRepository : JpaRepository<User, UserId> {
  fun existsByEmail(email: Email): Boolean
}