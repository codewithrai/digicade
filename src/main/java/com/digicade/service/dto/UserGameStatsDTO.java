package com.digicade.service.dto;

public class UserGameStatsDTO {

    private String name;
    private Long won;
    private Long lost;

    private Integer rank;

    public UserGameStatsDTO() {}

    public UserGameStatsDTO(String name, Long won, Long lost, Integer rank) {
        this.name = name;
        this.won = won;
        this.lost = lost;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
