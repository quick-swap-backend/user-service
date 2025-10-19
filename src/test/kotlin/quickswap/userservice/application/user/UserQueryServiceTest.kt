package quickswap.userservice.application.user

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import quickswap.commons.domain.shared.id.UserId
import quickswap.commons.domain.shared.vo.Email
import quickswap.userservice.application.user.ports.out.UserRepository
import quickswap.userservice.domain.user.User
import quickswap.userservice.fixture.UserFixture
import java.util.*

class UserQueryServiceTest {

  val repository: UserRepository = mockk()
  val queryService: UserQueryService = UserQueryService(repository)
  val user: User = UserFixture.createUser()

  @Test
  fun findByEmail() {
    every { repository.findByEmail(user.email) } returns user
    assert(queryService.findByEmail(user.email) == user)

    val unexistsEmail = Email("test12345@test.com")
    every { repository.findByEmail(unexistsEmail) } returns null
    assertThrows<IllegalArgumentException> { queryService.findByEmail(unexistsEmail) }
  }

  @Test
  fun findById() {
    every { repository.findById(user.id) } returns Optional.of(user)
    assert(queryService.findById(user.id) == user)

    val unexistsUserId = UserId(UUID.randomUUID().toString())
    every { repository.findById(unexistsUserId) } returns Optional.empty()
    assertThrows<IllegalArgumentException> { queryService.findById(unexistsUserId)}

  }

}