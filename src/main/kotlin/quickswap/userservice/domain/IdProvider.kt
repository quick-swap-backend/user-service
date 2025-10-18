package quickswap.userservice.domain

fun interface IdProvider {
  fun provide():String
}