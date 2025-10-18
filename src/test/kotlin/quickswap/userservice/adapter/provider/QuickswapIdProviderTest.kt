package quickswap.userservice.adapter.provider

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import quickswap.userservice.domain.shared.IdProvider

class QuickswapIdProviderTest {

  val idProvider: IdProvider = QuickswapIdProvider()

  @Test
  fun provide() {
    val id = idProvider.provide()
    assertTrue { !id.isEmpty() }
  }
}