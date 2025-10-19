package quickswap.userservice.application.user

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.BeforeEach
import quickswap.userservice.application.user.ports.out.UserRepository
import quickswap.userservice.domain.shared.IdProvider
import quickswap.userservice.domain.shared.PasswordEncoder
import quickswap.userservice.domain.user.DuplicateEmailException
import quickswap.userservice.fixture.SecurityFixture
import quickswap.userservice.fixture.UserFixture

class UserModifyServiceTest {

  private lateinit var userModifyService: UserModifyService
  private lateinit var userRepository: UserRepository
  private lateinit var idProvider: IdProvider
  private lateinit var passwordEncoder: PasswordEncoder

  @BeforeEach
  fun setUp() {
    userRepository = mockk()
    idProvider = UserFixture.idProvider
    passwordEncoder = SecurityFixture.getPasswordEncoder()

    userModifyService = UserModifyService(
      userRepository = userRepository,
      idProvider = idProvider,
      passwordEncoder = passwordEncoder
    )
  }

  @Test
  fun create() {
    val request = UserFixture.getUserCreateRequest()
    every { userRepository.existsByEmail(request.email) } returns false
    every { userRepository.save(any()) } answers { firstArg() }

    val user = userModifyService.create(request)

    assert(user.email == request.email)
    verify { userRepository.existsByEmail(request.email) }
    verify { userRepository.save(any()) }
  }

  @Test
  fun duplicateEmail() {
    val request = UserFixture.getUserCreateRequest()
    every { userRepository.existsByEmail(request.email) } returns true

    assertThrows<DuplicateEmailException> { userModifyService.create(request) }

    verify { userRepository.existsByEmail(request.email) }
    verify(exactly = 0) { userRepository.save(any()) }
  }
}