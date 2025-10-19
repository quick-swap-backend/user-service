package quickswap.userservice.domain.user

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class TelephoneTest {
  @Test
  fun validate() {
    assertThrows<IllegalArgumentException> { Telephone("010-12345-1234") }

    assertThrows<IllegalArgumentException> { Telephone("010-1234-123") }

    assertThrows<IllegalArgumentException> { Telephone("020-1234-1234") }

    Telephone("010-1234-5678")
    Telephone("010-123-4567")
    Telephone("01012345678")
    Telephone("011-123-4567")
  }

  @Test
  fun equals() {
    assertEquals(
      Telephone("010-1234-5678"),
      Telephone("010-1234-5678")
    )

    assertNotEquals(
      Telephone("010-1234-5678"),
      Telephone("010-1234-5671")
    )
  }
}