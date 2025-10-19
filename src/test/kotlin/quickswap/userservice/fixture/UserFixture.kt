package quickswap.userservice.fixture

import quickswap.userservice.domain.shared.IdProvider
import quickswap.userservice.domain.shared.PasswordEncoder
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