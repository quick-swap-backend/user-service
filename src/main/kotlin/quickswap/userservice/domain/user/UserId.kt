package quickswap.userservice.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import quickswap.userservice.domain.shared.IdProvider
import java.io.Serializable

@Embeddable
data class UserId(
  @Column(name = "id")
  val value: String
) : Serializable {
  constructor(provider: IdProvider) : this(provider.provide())
}