package com.digicade.service.mapper;

import com.digicade.domain.Game;
import com.digicade.domain.GameLevel;
import com.digicade.domain.Player;
import com.digicade.service.dto.GameDTO;
import com.digicade.service.dto.GameLevelDTO;
import com.digicade.service.dto.PlayerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GameLevel} and its DTO {@link GameLevelDTO}.
 */
@Mapper(componentModel = "spring")
public interface GameLevelMapper extends EntityMapper<GameLevelDTO, GameLevel> {
    @Mapping(target = "player", source = "player", qualifiedByName = "playerId")
    @Mapping(target = "game", source = "game", qualifiedByName = "gameId")
    GameLevelDTO toDto(GameLevel s);

    @Named("playerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PlayerDTO toDtoPlayerId(Player player);

    @Named("gameId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GameDTO toDtoGameId(Game game);
}
