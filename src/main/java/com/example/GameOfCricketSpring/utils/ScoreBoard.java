package com.example.GameOfCricketSpring.utils;

import com.example.GameOfCricketSpring.model.Inning;
import com.example.GameOfCricketSpring.model.Player;
import com.example.GameOfCricketSpring.model.Team;
import com.example.GameOfCricketSpring.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScoreBoard {

    @Autowired
    TeamService teamService;

    public void displayScoreBoard(Inning inning, List<Player> battingTeamPlayers, List<Player> bowlingTeamPlayers) {
        Team battingTeam = teamService.findById(inning.getBattingTeamId());
        Team bowlingTeam = teamService.findById(inning.getBowlingTeamId());
        System.out.println("\n" + battingTeam.getName() + " batting, " + bowlingTeam.getName() + " bowling...");
        displayScoreLine(inning.getScoreLine());
        displayBattingData(battingTeamPlayers);
        displayBowlingData(bowlingTeamPlayers);
        displayInningTotals(inning);
    }

    private void displayScoreLine(List<Character> scoreLine) {
        StringBuilder sb = new StringBuilder("ScoreLine: ");
        for (int i = 0; i < scoreLine.size(); i++) {
            char bowlScore = scoreLine.get(i);
            sb.append(bowlScore);
            if ((i + 1) % 6 == 0) {
                sb.append(" | ");
            } else {
                sb.append(',');
            }
        }
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }
        System.out.println(sb);
    }

    private void displayBattingData(List<Player> battingTeamPlayers) {
        StringBuilder sb = new StringBuilder("Batting: ");
        for (Player player : battingTeamPlayers) {
            if (player.getBallsPlayed() != 0) {
                sb.append("\n\t").append(player.getName()).append(": ");
                sb.append(player.getRunsScored()).append(" (");
                sb.append(player.getBallsPlayed()).append(")");
            }
        }
        System.out.println(sb);
    }

    private void displayBowlingData(List<Player> bowlingTeamPlayers) {
        StringBuilder sb = new StringBuilder("Bowling: ");
        for (Player player : bowlingTeamPlayers) {
            if (player.getBallsBowled() != 0) {
                int overs = player.getBallsBowled() / 6;
                int balls = player.getBallsBowled() % 6;
                sb.append("\n\t").append(player.getName()).append(": ");
                sb.append(player.getWickets()).append("-");
                sb.append(player.getRunsGiven()).append(" (");
                sb.append(overs).append(".");
                sb.append(balls).append(")");
            }
        }
        System.out.println(sb);
    }

    private void displayInningTotals(Inning inning) {
        int overs = inning.getScoreLine().size() / 6;
        int balls = inning.getScoreLine().size() % 6;
        StringBuilder sb = new StringBuilder("Total: ");
        sb.append(inning.getTotalRuns()).append(" / ");
        sb.append(inning.getTotalWickets()).append(" ");
        sb.append("(").append(overs).append(".");
        sb.append(balls).append(")");
        System.out.println(sb);
    }
}