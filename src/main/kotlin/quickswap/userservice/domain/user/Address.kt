package quickswap.userservice.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Address(
  @Column(name = "city")
  val city: String,

  @Column(name = "street")
  val street: String,

  @Column(name = "zipcode")
  val zipCode: String,

  ) {

  companion object {
    private val ZIP_CODE_REGEX = "^(\\d{5}|\\d{3}-\\d{3})$".toRegex()
  }

  init {
    require(city.isNotBlank()) { "도시는 비어있을 수 없습니다" }
    require(street.isNotBlank()) { "거리 주소는 비어있을 수 없습니다" }
    require(zipCode.matches(ZIP_CODE_REGEX)) {
      "유효하지 않은 우편번호 형식입니다: $zipCode"
    }
  }
}