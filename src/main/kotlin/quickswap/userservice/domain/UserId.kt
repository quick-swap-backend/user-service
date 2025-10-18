package quickswap.userservice.domain

class UserId(provider: IdProvider) {

  val value: String = provider.provide()

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as UserId

    return value == other.value
  }

  override fun hashCode(): Int {
    return value.hashCode()
  }

}
