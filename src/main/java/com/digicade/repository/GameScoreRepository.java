package com.digicade.repository;

import com.digicade.domain.GameScore;
import com.digicade.service.dto.UserGameStatsDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the GameScore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameScoreRepository extends JpaRepository<GameScore, Long> {
    @Query("SELECT new com.digicade.service.dto.UserGameStatsDTO(game.name, " +
        "max(gameScore.score), sum(gameScore.won), sum(gameScore.lost)) " +
        "from GameScore gameScore inner join Game game on game.id = gameScore.game.id " +
        "where gameScore.player.id = :id group by game.url order by max(gameScore.score) desc")
    List<UserGameStatsDTO> findGameScoreByPlayerIdOrderByScoreDesc(@Param("id") Long id);

//    List<GameScore> findGameScoreByPlayerIdOrderByScoreDesc(Long id);
}
