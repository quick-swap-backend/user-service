package quickswap.userservice.domain.user

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class PasswordTest {

  @Test
  fun validate() {
    assertThrows<IllegalArgumentException> {
      Password("a")
      Password("abcd")
      Password("abcde ")
      Password("abcd e")
    }

    Password("abcdef")
  }

  @Test
  fun equals() {
    assertEquals(
      Password("abcdef"),
      Password("abcdef")
    )
    assertNotEquals(
      Password("abcdef"),
      Password("abcdefg")
    )
  }
}