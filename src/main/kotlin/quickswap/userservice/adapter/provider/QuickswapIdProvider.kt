package quickswap.userservice.adapter.provider

import com.github.f4b6a3.ulid.UlidCreator
import org.springframework.stereotype.Component
import quickswap.userservice.domain.shared.IdProvider

@Component
class QuickswapIdProvider(): IdProvider {

  override fun provide(): String {
    return UlidCreator.getUlid().toString()
  }

}