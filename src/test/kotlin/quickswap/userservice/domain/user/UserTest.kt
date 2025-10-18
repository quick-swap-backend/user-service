package quickswap.userservice.domain.user

import org.junit.jupiter.api.Test
import quickswap.userservice.domain.shared.IdProvider
import quickswap.userservice.domain.shared.PasswordEncoder
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserTest {

  val idProvider: IdProvider = IdProvider { UUID.randomUUID().toString() }
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

    val user = User.of(
      userId, email, password, passwordEncoder, address, telephone, reputation
    )

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
      userId, Email("abc@test.com"), Password("test@123"), passwordEncoder,
      Address("부산특별시", "광안리", "123-456"), Telephone("010-1234-5678"), Reputation(3.3f)
    )

    val userB = User.of(
      userId, Email("efg@test.com"), Password("test@456"), passwordEncoder,
      Address("서울특별시", "강서구", "456-123"), Telephone("010-5678-1234"), Reputation(1.3f)
    )

    assertEquals(userA, userB)
  }

  @Test
  fun verifyPassword() {
    val userId = UserId(idProvider)

    val user = User.of(
      userId, Email("abc@test.com"), Password("test@123"), passwordEncoder,
      Address("부산특별시", "광안리", "123-456"), Telephone("010-1234-5678"), Reputation(3.3f)
    )

    assertTrue {
      user.verifyPassword(Password("test@123"), passwordEncoder)
      !user.verifyPassword(Password("test@1234"), passwordEncoder)
    }
  }

  @Test
  fun changePassword() {
    val userId = UserId(idProvider)
    val oldPassword = "test@123"
    val newPassword = "abcd@123"

    val user = User.of(
      userId, Email("abc@test.com"), Password(oldPassword), passwordEncoder,
      Address("부산특별시", "광안리", "123-456"), Telephone("010-1234-5678"), Reputation(3.3f)
    )

    user.changePassword(Password(oldPassword), passwordEncoder)

    assertTrue {
      user.verifyPassword(Password(oldPassword), passwordEncoder)
      !user.verifyPassword(Password(newPassword), passwordEncoder)
    }

  }
}