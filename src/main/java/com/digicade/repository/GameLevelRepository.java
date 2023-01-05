package com.digicade.repository;

import com.digicade.domain.GameLevel;
import com.digicade.service.dto.GameLevelDTO;
import com.digicade.service.dto.LeaderBoardDTO;
import com.digicade.service.dto.XPRankDTO;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GameLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameLevelRepository extends JpaRepository<GameLevel, Long> {
    Optional<List<GameLevel>> findByGameIdOrderByXpAsc(Long id);
    Optional<List<GameLevel>> findByGameIdAndCreatedAtBetweenOrderByXpAsc(Long id, Timestamp start, Timestamp end);
    Optional<GameLevel> findByGameIdAndPlayerId(Long gameId, Long playerId);

    @Query(
        "SELECT new com.digicade.service.dto.LeaderBoardDTO(gameLevel.player.user.id, gameLevel.player.user.firstName, gameLevel.player.user.lastName, gameLevel.level, highScore.highestScore) " +
        "FROM GameLevel gameLevel inner join HighScore highScore on " +
        "highScore.player.id = gameLevel.player.id " +
        "where gameLevel.game.id = :gameId and gameLevel.createdAt between :start and :end " +
        " order by gameLevel.level asc "
    )
    Optional<List<LeaderBoardDTO>> findLeaderBoardByGameIdByGameIdWithDateRange(
        @Param("gameId") Long id,
        @Param("start") Timestamp start,
        @Param("end") Timestamp end
    );

    @Query(
        "SELECT new com.digicade.service.dto.LeaderBoardDTO(gameLevel.player.user.id, gameLevel.player.user.firstName, gameLevel.player.user.lastName, gameLevel.level, highScore.highestScore) " +
        "FROM GameLevel gameLevel inner join HighScore highScore on " +
        "highScore.player.id = gameLevel.player.id " +
        "where gameLevel.game.id = :gameId and gameLevel.createdAt between :start and :end " +
        " order by gameLevel.level asc "
    )
    Optional<List<LeaderBoardDTO>> findLeaderBoardByGameId(@Param("gameId") Long id);

    @Query(
        "SELECT new com.digicade.service.dto.XPRankDTO(sum(gameLevel.level), " +
        "sum(gameLevel.xp)) from GameLevel gameLevel " +
        "where gameLevel.player.id = :playerId"
    )
    Optional<XPRankDTO> findXpAndRankByPlayerId(@Param("playerId") Long playerId);
}
