package com.digicade.repository;

import com.digicade.domain.PlayerGameBadge;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlayerGameBadge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerGameBadgeRepository extends JpaRepository<PlayerGameBadge, Long> {}
