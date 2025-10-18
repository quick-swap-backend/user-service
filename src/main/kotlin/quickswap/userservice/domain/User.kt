package quickswap.userservice.domain

data class User(

  val id: UserId,

  var email: Email,

  var hashedPassword: String,

  var address: Address,

  var telephone: Telephone,

  /* 평판 평균 점수. 1~5 */
  var reputation: Reputation,

){
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as User

    return id == other.id
  }

  override fun hashCode(): Int {
    return id.hashCode()
  }
}
