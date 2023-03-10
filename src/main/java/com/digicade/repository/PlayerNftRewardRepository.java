package com.digicade.repository;

import com.digicade.domain.PlayerNftReward;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlayerNftReward entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerNftRewardRepository extends JpaRepository<PlayerNftReward, Long> {
    Optional<PlayerNftReward> findByPlayerId(Long id);
}
