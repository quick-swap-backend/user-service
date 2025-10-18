package quickswap.userservice.domain.user

import org.junit.jupiter.api.Test
import quickswap.userservice.domain.shared.IdProvider
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

class UserIdTest {

  val idProvider: IdProvider = IdProvider { LocalDateTime.now().toString() + UUID.randomUUID() }

  @Test
  fun validate() {
    assertFalse {
      UserId(idProvider).value.isEmpty()
    }
  }

  @Test
  fun equals() {
    assertEquals(
      UserId { "test" },
      UserId { "test" }
    )

    assertEquals(
      UserId { "test" }.value,
      "test"
    )

    assertNotEquals(
      UserId { "test" }.value,
      "test1"
    )
  }
}