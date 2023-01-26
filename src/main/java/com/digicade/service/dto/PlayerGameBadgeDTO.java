package com.digicade.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.digicade.domain.PlayerGameBadge} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlayerGameBadgeDTO implements Serializable {

    private Long id;

    private GameBadgeDTO gameBadge;

    private PlayerDTO player;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameBadgeDTO getGameBadge() {
        return gameBadge;
    }

    public void setGameBadge(GameBadgeDTO gameBadge) {
        this.gameBadge = gameBadge;
    }

    public PlayerDTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDTO player) {
        this.player = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlayerGameBadgeDTO)) {
            return false;
        }

        PlayerGameBadgeDTO playerGameBadgeDTO = (PlayerGameBadgeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, playerGameBadgeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlayerGameBadgeDTO{" +
            "id=" + getId() +
            ", gameBadge=" + getGameBadge() +
            ", player=" + getPlayer() +
            "}";
    }
}
