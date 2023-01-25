package com.digicade.repository;

import com.digicade.domain.PlayerCouponReward;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlayerCouponReward entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerCouponRewardRepository extends JpaRepository<PlayerCouponReward, Long> {
    Optional<PlayerCouponReward> findByPlayerId(Long id);
}
