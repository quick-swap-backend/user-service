package quickswap.userservice.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Email(

  @Column(name = "email", nullable = false)
  val value: String

) {

  companion object {
    private val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
  }
  init {
    require(value.matches(EMAIL_REGEX)) {
      "유효하지 않은 이메일 형식입니다: $value"
    }
  }
}
