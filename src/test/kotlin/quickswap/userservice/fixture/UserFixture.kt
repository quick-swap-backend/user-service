package quickswap.userservice.fixture

import quickswap.commons.domain.shared.IdProvider
import quickswap.commons.domain.shared.PasswordEncoder
import quickswap.commons.domain.shared.id.UserId
import quickswap.commons.domain.shared.vo.Address
import quickswap.commons.domain.shared.vo.Email
import quickswap.commons.domain.shared.vo.Password
import quickswap.commons.domain.shared.vo.Telephone
import quickswap.userservice.domain.user.*
import java.util.UUID

class UserFixture {
  companion object {

    val idProvider: IdProvider = IdProvider { UUID.randomUUID().toString() }

    fun createUser(
      userId: UserId = UserId(idProvider),
      request: UserCreateRequest = getUserCreateRequest(),
      passwordEncoder: PasswordEncoder = SecurityFixture.getPasswordEncoder(),
    ): User {
      return User.of(userId, request, passwordEncoder)
    }

    fun getUserCreateRequest(
      email: Email = Email("abc@test.com"),
      password: Password = Password("test@123"),
      address: Address = Address("부산특별시", "광안리", "123-456"),
      telephone: Telephone = Telephone("010-1234-5678"),
    ): UserCreateRequest {
      return UserCreateRequest(email, password, address, telephone)
    }
  }
}