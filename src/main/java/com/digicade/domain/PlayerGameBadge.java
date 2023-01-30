package com.digicade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A PlayerGameBadge.
 */
@Entity
@Table(name = "player_game_badge")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlayerGameBadge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JsonIgnoreProperties(value = { "playerGameBadges", "game", "player" }, allowSetters = true)
    private GameBadge gameBadge;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "gameBadges",
            "playerGameBadges",
            "gameLevels",
            "gameScores",
            "highScores",
            "playerCouponRewards",
            "playerNftRewards",
            "transactions",
            "digiUser",
        },
        allowSetters = true
    )
    private Player player;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlayerGameBadge id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public GameBadge getGameBadge() {
        return this.gameBadge;
    }

    public void setGameBadge(GameBadge gameBadge) {
        this.gameBadge = gameBadge;
    }

    public PlayerGameBadge gameBadge(GameBadge gameBadge) {
        this.setGameBadge(gameBadge);
        return this;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PlayerGameBadge player(Player player) {
        this.setPlayer(player);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlayerGameBadge)) {
            return false;
        }
        return id != null && id.equals(((PlayerGameBadge) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlayerGameBadge{" +
            "id=" + getId() +
            "}";
    }
}
