package quickswap.userservice.domain.user

import org.junit.jupiter.api.Test
import quickswap.userservice.domain.shared.IdProvider
import quickswap.userservice.domain.shared.PasswordEncoder
import quickswap.userservice.fixture.UserFixture
import quickswap.userservice.fixture.UserFixture.Companion.createUser
import quickswap.userservice.fixture.UserFixture.Companion.getUserCreateRequest
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserTest {

  val idProvider: IdProvider = UserFixture.idProvider
  val passwordEncoder: PasswordEncoder = object : PasswordEncoder {

    override fun encode(password: String): String {
      return "hashed_$password"
    }

    override fun matches(password: String, hashedPassword: String): Boolean {
      return encode(password) == hashedPassword
    }
  }

  @Test
  fun init() {
    val userId = UserId(idProvider)
    val email = Email("abc@test.com")
    val password = Password("test@123")
    val address = Address("부산특별시", "광안리", "123-456")
    val telephone = Telephone("010-1234-5678")
    val reputation = Reputation(3.3f)

    val request = UserCreateRequest(email, password, address, telephone)

    val user = User.of(userId, request, passwordEncoder, reputation)

    assertEquals(user.id, userId)
    assertEquals(user.email, email)
    assertEquals(user.address, address)
    assertEquals(user.telephone, telephone)
    assertEquals(user.reputation, reputation)
  }

  @Test
  fun equals() {

    val userId = UserId(idProvider)

    val userA = User.of(
      userId,
      UserCreateRequest(
        Email("abc@test.com"), Password("test@123"),
        Address("부산특별시", "광안리", "123-456"), Telephone("010-1234-5678")
      ),
      passwordEncoder,
      Reputation(3.3f)
    )

    val userB = createUser(userId = userId)

    assertEquals(userA, userB)
  }

  @Test
  fun verifyPassword() {
    val userId = UserId(idProvider)

    val user = User.of(
      userId,
      getUserCreateRequest(password = Password("test@123")),
      passwordEncoder,
      Reputation(3.3f)
    )

    assertTrue { user.verifyPassword(Password("test@123"), passwordEncoder) }

    assertTrue { !user.verifyPassword(Password("test@1234"), passwordEncoder) }
  }

  @Test
  fun changePassword() {
    val userId = UserId(idProvider)
    val oldPassword = "test@123"
    val newPassword = "abcd@123"

    val user = createUser(userId = userId, request = getUserCreateRequest(password = Password(oldPassword)))

    user.changePassword(Password(oldPassword), passwordEncoder)

    assertTrue {
      user.verifyPassword(Password(oldPassword), passwordEncoder)
      !user.verifyPassword(Password(newPassword), passwordEncoder)
    }

  }
}