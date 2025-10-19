package quickswap.userservice.application.user

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import quickswap.userservice.application.user.ports.`in`.UserCreator
import quickswap.userservice.application.user.ports.out.UserRepository
import quickswap.userservice.domain.shared.IdProvider
import quickswap.userservice.domain.shared.PasswordEncoder
import quickswap.userservice.domain.user.DuplicateEmailException
import quickswap.userservice.domain.user.User
import quickswap.userservice.domain.user.UserCreateRequest
import quickswap.userservice.domain.user.UserId

@Transactional
@Service
class UserModifyService(

  private val userRepository: UserRepository,

  private val idProvider: IdProvider,

  private val passwordEncoder: PasswordEncoder,

): UserCreator {
  override fun create(request: UserCreateRequest): User {

    if (userRepository.existsByEmail(request.email)) {
      throw DuplicateEmailException("이미 사용 중인 이메일입니다: ${request.email.value}")
    }

    val user = User.of(UserId(idProvider), request, passwordEncoder)
    return userRepository.save(user)
  }

}