package quickswap.userservice.domain.user

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ReputationTest {
  @Test
  fun validate() {
    assertThrows<IllegalArgumentException> {
      Reputation(0.99f)
      Reputation(5.01f)
    }
    Reputation(1f)
    Reputation(5f)
  }

  @Test
  fun equals() {
    assertEquals(
      Reputation(3.51f),
      Reputation(3.51f)
    )

    assertNotEquals(
      Reputation(3.51f),
      Reputation(3.52f)
    )
  }
}