package com.digicade.service.dto;

public class UserGameHighScoresDTO {
    private Long id;
    private String gameName;
    private Integer highestScore;

    public UserGameHighScoresDTO() {
    }

    public UserGameHighScoresDTO(Long id, String gameName, Integer highestScore) {
        this.id = id;
        this.gameName = gameName;
        this.highestScore = highestScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Integer getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(Integer highestScore) {
        this.highestScore = highestScore;
    }
}
