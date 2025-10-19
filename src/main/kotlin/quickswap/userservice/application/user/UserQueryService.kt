package quickswap.userservice.application.user

import org.springframework.stereotype.Service
import quickswap.commons.domain.shared.id.UserId
import quickswap.commons.domain.shared.vo.Email
import quickswap.userservice.application.user.ports.`in`.UserFinder
import quickswap.userservice.application.user.ports.out.UserRepository
import quickswap.userservice.domain.user.User

@Service
class UserQueryService(

  private val userRepository: UserRepository

): UserFinder {

  override fun findByEmail(email: Email): User {
    return userRepository.findByEmail(email)
      ?: throw IllegalArgumentException("유저를 찾을 수 없습니다. email: ${email.value}")
  }

  override fun findById(id: UserId): User {
    return userRepository.findById(id).orElseThrow { IllegalArgumentException("유저를 찾을 수 없습니다. id: ${id.value}") }
  }

}