package quickswap.userservice.domain.outbox

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "outbox_events")
class OutboxEvent(

  @Id
  val id: String,

  @Column(name = "aggregate_type", nullable = false)
  val aggregateType: String,

  @Column(name = "aggregate_id", nullable = false)
  val aggregateId: String,

  @Column(name = "event_type", nullable = false)
  val eventType: String,

  @Column(name = "payload", columnDefinition = "JSON", nullable = false)
  val payload: String,

  @Column(name = "created_at", nullable = false)
  val createdAt: LocalDateTime = LocalDateTime.now()
) {
  companion object {
    fun create(
      aggregateType: String,
      aggregateId: String,
      eventType: String,
      payload: String
    ): OutboxEvent {
      return OutboxEvent(
        id = UUID.randomUUID().toString(),
        aggregateType = aggregateType,
        aggregateId = aggregateId,
        eventType = eventType,
        payload = payload
      )
    }
  }
}