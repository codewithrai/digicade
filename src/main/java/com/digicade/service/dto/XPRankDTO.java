package com.digicade.service.dto;

public class XPRankDTO {

    private Long rank;
    private Long xp;

    public XPRankDTO() {}

    public XPRankDTO(Long rank, Long xp) {
        this.rank = rank;
        this.xp = xp;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public Long getXp() {
        return xp;
    }

    public void setXp(Long xp) {
        this.xp = xp;
    }

    @Override
    public String toString() {
        return "XPRankDTO{" + "rank=" + rank + ", xp=" + xp + '}';
    }
}
