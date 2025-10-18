package quickswap.userservice.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Telephone(

  @Column(name = "telephone")
  val value: String

) {

  /*
  * 허용 하는 형식 예시.
  * 010-1234-5678
  * 010-123-4567
  * 01012345678
  * 011-123-4567
  * */
  companion object {
    private val TELEPHONE_REGEX = "^01(?:0|1|[6-9])-?(?:\\d{3}|\\d{4})-?\\d{4}$".toRegex()
  }

  init {
    require(value.matches(TELEPHONE_REGEX)) {
      "유효하지 않은 연락처 형식입니다: $value"
    }
  }
}
