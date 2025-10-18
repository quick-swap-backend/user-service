package quickswap.userservice.adapter.security

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class QuickswapPasswordEncoderTest {

  val passwordEncoder = QuickswapPasswordEncoder()

  @Test
  fun encode() {
    val myPassword = "test@1234"

    val encoded = passwordEncoder.encode(myPassword)

    assertTrue { !encoded.isEmpty() }

    assertTrue { encoded != myPassword }
  }

  @Test
  fun matches() {
    val myPassword = "test@1234"

    val encoded = passwordEncoder.encode(myPassword)

    assertTrue { passwordEncoder.matches(myPassword, encoded) }
  }
}