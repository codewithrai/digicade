package com.digicade.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.digicade.domain.GameBadge} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GameBadgeDTO implements Serializable {

    private Long id;

    private Integer xp;

    private String logoUrl;

    private GameDTO game;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public GameDTO getGame() {
        return game;
    }

    public void setGame(GameDTO game) {
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameBadgeDTO)) {
            return false;
        }

        GameBadgeDTO gameBadgeDTO = (GameBadgeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, gameBadgeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GameBadgeDTO{" +
            "id=" + getId() +
            ", logoUrl='" + getLogoUrl() + "'" +
            ", game=" + getGame() +
            "}";
    }
}
