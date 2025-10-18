package quickswap.userservice.domain

data class UserId(val value: String) {
  constructor(provider: IdProvider) : this(provider.provide())
}