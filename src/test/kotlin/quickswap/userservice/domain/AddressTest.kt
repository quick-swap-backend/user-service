package quickswap.userservice.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class AddressTest {

  @Test
  fun validate() {
    assertThrows<IllegalArgumentException> {
      Address("", "사당로 20길", "12345")
      Address("서울특별시", "", "12345")
      Address("서울특별시", "사당로 20길", "")

      Address("서울특별시", "사당로 20길", "1234")
      Address("서울특별시", "사당로 20길", "123456")
      Address("서울특별시", "사당로 20길", "123--456")
    }

    Address("서울특별시", "사당로 20길", "12345")
  }

  @Test
  fun equals() {
    assertEquals(
      Address("서울특별시", "사당로 20길", "12345"),
      Address("서울특별시", "사당로 20길", "12345")
    )
  }

}