package com.example.GameOfCricketSpring.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "inning")
@NoArgsConstructor
@Getter
@Setter
public class Inning {

    private String id;
    private String battingTeamId;
    private String bowlingTeamId;
    private int totalRuns;
    private int totalWickets;
    private List<Character> scoreLine = new ArrayList<>();

    public Inning(String battingTeamId, String bowlingTeamId) {
        this.battingTeamId = battingTeamId;
        this.bowlingTeamId = bowlingTeamId;
    }
}