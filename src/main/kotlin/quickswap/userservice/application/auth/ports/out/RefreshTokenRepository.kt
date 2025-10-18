package quickswap.userservice.application.auth.ports.out

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import quickswap.userservice.adapter.persistence.auth.RefreshTokenEntity
import java.time.LocalDateTime

interface RefreshTokenRepository : JpaRepository<RefreshTokenEntity, Long> {
  @Query(
    """
    select count(r) > 0
    from RefreshTokenEntity r 
    where r.userId = :userId 
      and r.isForceExpired = false 
      and r.expiryDate > :now
    """
  )
  fun existsActiveTokenByUserId(
    @Param("userId") userId: String,
    @Param("now") now: LocalDateTime = LocalDateTime.now()
  ): Boolean

  @Modifying
  @Query(
    """
    update RefreshTokenEntity t 
    set t.isForceExpired = true 
    where t.userId = :userId
    """
  )
  fun forceExpireAllByUserId(@Param("userId") userId: String)

  fun findByToken(refreshToken: String): RefreshTokenEntity?
}