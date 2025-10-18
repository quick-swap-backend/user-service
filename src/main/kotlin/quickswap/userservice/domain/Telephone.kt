package quickswap.userservice.domain

data class Telephone(val value: String) {

  companion object {
    /*
    * 허용 하는 형식 예시.
    * 010-1234-5678
    * 010-123-4567
    * 01012345678
    * 011-123-4567
    * */
    private val TELEPHONE_REGEX = "^01(?:0|1|[6-9])-?(?:\\d{3}|\\d{4})-?\\d{4}$".toRegex()
  }

  init {
    require(value.matches(TELEPHONE_REGEX)) {
      "유효하지 않은 연착처 형식입니다: $value"
    }
  }
}
