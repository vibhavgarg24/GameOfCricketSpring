package com.example.GameOfCricketSpring.model;

import com.example.GameOfCricketSpring.enums.PlayerType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "playerStat")
@NoArgsConstructor
@Getter
@Setter
public class PlayerStat {

    private String id;
    private String playerId;
    private String name;
    private int runsScored;
    private int runsGiven;
    private int wickets;
    private int ballsPlayed;
    private int ballsBowled;
    private PlayerType playerType;

    public PlayerStat(Player player) {
        playerId = player.getId();
        name = player.getName();
        runsScored = player.getRunsScored();
        runsGiven = player.getRunsGiven();
        wickets = player.getWickets();
        ballsPlayed = player.getBallsPlayed();
        ballsBowled = player.getBallsBowled();
        playerType = player.getPlayerType();
    }
}