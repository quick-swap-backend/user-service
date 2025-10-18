package quickswap.userservice.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class TelephoneTest {
  @Test
  fun validate() {
    assertThrows<IllegalArgumentException> {
      Telephone("010-12345-1234")
      Telephone("010-1234-123")
      Telephone("020-1234-1234")
    }

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
  }
}