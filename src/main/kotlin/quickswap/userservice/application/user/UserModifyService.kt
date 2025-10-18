package quickswap.userservice.application.user

import org.springframework.stereotype.Service
import quickswap.userservice.application.user.ports.`in`.UserCreator
import quickswap.userservice.application.user.ports.out.UserRepository
import quickswap.userservice.domain.shared.IdProvider
import quickswap.userservice.domain.shared.PasswordEncoder
import quickswap.userservice.domain.user.User
import quickswap.userservice.domain.user.UserCreateRequest
import quickswap.userservice.domain.user.UserId

@Service
class UserModifyService(

  val userRepository: UserRepository,

  val idProvider: IdProvider,

  val passwordEncoder: PasswordEncoder,

): UserCreator {
  override fun create(request: UserCreateRequest): User {
    val user = User.of(UserId(idProvider), request, passwordEncoder)
    return userRepository.save(user)
  }

}