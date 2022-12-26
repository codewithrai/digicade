package com.digicade.repository;

import com.digicade.domain.GameBadge;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Spring Data JPA repository for the GameBadge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameBadgeRepository extends JpaRepository<GameBadge, Long> {
    Set<GameBadge> findGameBadgeByPlayerId(Long id);
}
