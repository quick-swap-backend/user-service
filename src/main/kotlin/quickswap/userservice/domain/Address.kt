package quickswap.userservice.domain

data class Address(

  var city: String,

  var address: String,

  var zipCode: String,

  ) {

  companion object {
    private val ZIP_CODE_REGEX = "^(\\d{5}|\\d{3}-\\d{3})$".toRegex()
  }

  init {
    require(city.isNotBlank()) { "도시는 비어있을 수 없습니다" }
    require(address.isNotBlank()) { "주소는 비어있을 수 없습니다" }
    require(zipCode.matches(ZIP_CODE_REGEX)) {
      "유효하지 않은 우편번호 형식입니다: $zipCode"
    }
  }
}