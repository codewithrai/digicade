package com.digicade.repository;

import com.digicade.domain.GameLevel;
import com.digicade.service.dto.GameLevelDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the GameLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameLevelRepository extends JpaRepository<GameLevel, Long> {
    Optional<List<GameLevel>> findByGameIdOrderByScoreDesc(Long id);
    Optional<List<GameLevel>> findByGameIdAndCreatedAtBetweenOrderByScoreDesc(Long id, Timestamp start, Timestamp end);
    Optional<GameLevel> findByGameIdAndPlayerId(Long gameId, Long playerId);
}
