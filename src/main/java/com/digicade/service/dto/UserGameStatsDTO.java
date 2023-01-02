package com.digicade.service.dto;

public class UserGameStatsDTO {
    private String name;
    private Integer score;
    private Long won;
    private Long lost;

    public UserGameStatsDTO() {
    }

    public UserGameStatsDTO(String name, Integer score, Long won, Long lost) {
        this.name = name;
        this.score = score;
        this.won = won;
        this.lost = lost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getWon() {
        return won;
    }

    public void setWon(Long won) {
        this.won = won;
    }

    public Long getLost() {
        return lost;
    }

    public void setLost(Long lost) {
        this.lost = lost;
    }
}
