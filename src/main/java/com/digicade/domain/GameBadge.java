package com.digicade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A GameBadge.
 */
@Entity
@Table(name = "game_badge")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GameBadge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "logo_url")
    private String logoUrl;

    @OneToMany(mappedBy = "gameBadge")
    @JsonIgnoreProperties(value = { "gameBadge", "player" }, allowSetters = true)
    private Set<PlayerGameBadge> playerGameBadges = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "gameBadges", "gameLevels", "gameScores", "highScores" }, allowSetters = true)
    private Game game;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GameBadge id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogoUrl() {
        return this.logoUrl;
    }

    public GameBadge logoUrl(String logoUrl) {
        this.setLogoUrl(logoUrl);
        return this;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Set<PlayerGameBadge> getPlayerGameBadges() {
        return this.playerGameBadges;
    }

    public void setPlayerGameBadges(Set<PlayerGameBadge> playerGameBadges) {
        if (this.playerGameBadges != null) {
            this.playerGameBadges.forEach(i -> i.setGameBadge(null));
        }
        if (playerGameBadges != null) {
            playerGameBadges.forEach(i -> i.setGameBadge(this));
        }
        this.playerGameBadges = playerGameBadges;
    }

    public GameBadge playerGameBadges(Set<PlayerGameBadge> playerGameBadges) {
        this.setPlayerGameBadges(playerGameBadges);
        return this;
    }

    public GameBadge addPlayerGameBadge(PlayerGameBadge playerGameBadge) {
        this.playerGameBadges.add(playerGameBadge);
        playerGameBadge.setGameBadge(this);
        return this;
    }

    public GameBadge removePlayerGameBadge(PlayerGameBadge playerGameBadge) {
        this.playerGameBadges.remove(playerGameBadge);
        playerGameBadge.setGameBadge(null);
        return this;
    }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public GameBadge game(Game game) {
        this.setGame(game);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameBadge)) {
            return false;
        }
        return id != null && id.equals(((GameBadge) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GameBadge{" +
            "id=" + getId() +
            ", logoUrl='" + getLogoUrl() + "'" +
            "}";
    }
}
