package com.digicade.service.dto;

import java.util.List;
import java.util.Set;

public class LeaderBoard {

    private List<LeaderBoardDTO> leaderBoard;

    public LeaderBoard() {
    }

    public LeaderBoard(List<LeaderBoardDTO> leaderBoard) {
        this.leaderBoard = leaderBoard;
    }

    public List<LeaderBoardDTO> getLeaderBoard() {
        return leaderBoard;
    }

    public void setLeaderBoard(List<LeaderBoardDTO> leaderBoard) {
        this.leaderBoard = leaderBoard;
    }
}
