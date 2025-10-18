package quickswap.userservice.domain.shared

fun interface IdProvider {
  fun provide():String
}