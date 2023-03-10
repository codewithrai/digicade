package com.digicade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

/**
 * A GameLevel.
 */
@Entity
@Table(name = "game_level")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GameLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "level")
    private Integer level;

    @Column(name = "xp")
    private Integer xp;

    private Timestamp createdAt;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "gameScores", "highScores", "gameLevels", "gameBadges", "transactions", "playerCouponRewards", "playerNftRewards", "digiUser",
        },
        allowSetters = true
    )
    private Player player;

    @ManyToOne
    @JsonIgnoreProperties(value = { "gameScores", "highScores", "gameBadges", "gameLevels" }, allowSetters = true)
    private Game game;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GameLevel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return this.level;
    }

    public GameLevel level(Integer level) {
        this.setLevel(level);
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public GameLevel score(Integer score) {
        return this;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GameLevel player(Player player) {
        this.setPlayer(player);
        return this;
    }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public GameLevel game(Game game) {
        this.setGame(game);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameLevel)) {
            return false;
        }
        return id != null && id.equals(((GameLevel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GameLevel{" +
            "id=" + getId() +
            ", level=" + getLevel() +
            "}";
    }
}
