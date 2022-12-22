package com.digicade.service.mapper;

import com.digicade.domain.DigiUser;
import com.digicade.domain.Player;
import com.digicade.service.dto.DigiUserDTO;
import com.digicade.service.dto.PlayerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DigiUser} and its DTO {@link DigiUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface DigiUserMapper extends EntityMapper<DigiUserDTO, DigiUser> {
    @Mapping(target = "player", source = "player", qualifiedByName = "playerId")
    DigiUserDTO toDto(DigiUser s);

    @Named("playerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PlayerDTO toDtoPlayerId(Player player);
}
