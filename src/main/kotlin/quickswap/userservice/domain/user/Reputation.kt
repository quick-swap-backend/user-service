package quickswap.userservice.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

/* 평판 평균 점수. 1~5. 거래 기록이 없으면 null 일 수 있음 */
@Embeddable
data class Reputation(
  @Column(name = "reputation")
  val value: Float?
) {
  init {
    value?.let {
      require(it in 1.0f..5.0f) {
        "평판 점수는 1.0에서 5.0 사이여야 합니다: $it"
      }
    }
  }
}