package com.digicade.service.mapper;

import com.digicade.domain.GameBadge;
import com.digicade.domain.Player;
import com.digicade.domain.PlayerGameBadge;
import com.digicade.service.dto.GameBadgeDTO;
import com.digicade.service.dto.PlayerDTO;
import com.digicade.service.dto.PlayerGameBadgeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlayerGameBadge} and its DTO {@link PlayerGameBadgeDTO}.
 */
@Mapper(componentModel = "spring")
public interface PlayerGameBadgeMapper extends EntityMapper<PlayerGameBadgeDTO, PlayerGameBadge> {
    @Mapping(target = "gameBadge", source = "gameBadge", qualifiedByName = "gameBadgeId")
    @Mapping(target = "player", source = "player", qualifiedByName = "playerId")
    PlayerGameBadgeDTO toDto(PlayerGameBadge s);

    @Named("gameBadgeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GameBadgeDTO toDtoGameBadgeId(GameBadge gameBadge);

    @Named("playerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PlayerDTO toDtoPlayerId(Player player);
}
