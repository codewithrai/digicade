package com.digicade.repository;

import com.digicade.domain.HighScore;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the HighScore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HighScoreRepository extends JpaRepository<HighScore, Long> {
    Optional<HighScore> findByGameIdAndPlayerId(Long gameId, Long playerId);

    List<HighScore> findByPlayerId(Long id);
}
