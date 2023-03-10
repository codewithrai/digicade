package com.digicade.repository;

import com.digicade.domain.Game;
import com.digicade.domain.GameBadge;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Game entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("SELECT game from Game game")
    Optional<List<Game>> findAllGames();
}
