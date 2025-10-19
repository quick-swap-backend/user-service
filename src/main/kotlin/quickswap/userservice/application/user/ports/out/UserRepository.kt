package quickswap.userservice.application.user.ports.out

import org.springframework.data.jpa.repository.JpaRepository
import quickswap.commons.domain.shared.id.UserId
import quickswap.commons.domain.shared.vo.Email
import quickswap.userservice.domain.user.User

interface UserRepository : JpaRepository<User, UserId> {

  fun existsByEmail(email: Email): Boolean

  fun findByEmail(email: Email): User?
}