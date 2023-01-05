package com.digicade.repository;

import com.digicade.domain.GameScore;
import com.digicade.service.dto.UserGameStatsDTO;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GameScore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameScoreRepository extends JpaRepository<GameScore, Long> {
    @Query(
        "SELECT new com.digicade.service.dto.UserGameStatsDTO(game.name, " +
        "sum(gameScore.won), sum(gameScore.lost), gameLevel.level) " +
        "from GameScore gameScore inner join Game game on game.id = gameScore.game.id " +
        "inner join GameLevel gameLevel on gameLevel.game.id = game.id " +
        "where gameScore.player.id = :id AND gameLevel.player.id = :id group by game.url order by max(gameScore.score) desc"
    )
    List<UserGameStatsDTO> findGameScoreByPlayerIdOrderByScoreDesc(@Param("id") Long id);
    //    List<GameScore> findGameScoreByPlayerIdOrderByScoreDesc(Long id);
}
