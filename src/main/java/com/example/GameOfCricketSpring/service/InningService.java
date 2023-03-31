package com.example.GameOfCricketSpring.service;

import com.example.GameOfCricketSpring.model.Inning;
import com.example.GameOfCricketSpring.model.Match;
import com.example.GameOfCricketSpring.model.Player;
import com.example.GameOfCricketSpring.model.Team;
import com.example.GameOfCricketSpring.repository.InningRepo;
import com.example.GameOfCricketSpring.utils.ScoreBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class InningService {

    @Autowired
    private InningRepo inningRepo;
    @Autowired
    private TeamService teamService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private PlayerStatService playerStatService;
    @Autowired
    private ScoreBoard scoreBoard;
    private Inning inning;
    private int targetRuns;
    private List<Player> battingTeamPlayers;
    private List<Player> bowlingTeamPlayers;
    private int strikePlayerIndex;
    private int nonStrikePlayerIndex;
    private int nextBatterIndex;
    private int currentBowlerIndex;

    public Inning addInning(String battingTeamId, String bowlingTeamId) {
        Inning newInning = new Inning(battingTeamId, bowlingTeamId);
        newInning.setId(UUID.randomUUID().toString());
        return inningRepo.save(newInning);
    }

    public Inning simulateInning(String inningId, int targetRuns, Match match) {
        inning = inningRepo.findById(inningId).get();
        this.targetRuns = targetRuns;
        config();
        simulateOvers(match.getOvers());
        saveData(match);
        scoreBoard.displayScoreBoard(inning, battingTeamPlayers, bowlingTeamPlayers);
        return inning;
    }

    private void config() {
        initPlayers();
        strikePlayerIndex = 0;
        nonStrikePlayerIndex = 1;
        nextBatterIndex = 2;
        currentBowlerIndex = 6;
    }

    private void initPlayers() {
        Team battingTeam = teamService.findById(inning.getBattingTeamId());
        List<String> battingTeamPlayerIds = battingTeam.getPlayerIds();
        battingTeamPlayers = new ArrayList<>(11);
        for (String playerId : battingTeamPlayerIds) {
            battingTeamPlayers.add(playerService.getDefaultPlayer(playerId));
        }
        Team bowlingTeam = teamService.findById(inning.getBowlingTeamId());
        List<String> bowlingTeamPlayerIds = bowlingTeam.getPlayerIds();
        bowlingTeamPlayers = new ArrayList<>(11);
        for (String playerId : bowlingTeamPlayerIds) {
            bowlingTeamPlayers.add(playerService.getDefaultPlayer(playerId));
        }
    }

    private void simulateOvers(int overs) {
        for (int i = 0; i < overs; i++) {
            for (int j = 0; j < 6; j++) {
                Player strikePlayer = battingTeamPlayers.get(strikePlayerIndex);
                int runsAtBall = Player.getRunsAtBall(strikePlayer);
                strikePlayer.setBallsPlayed(strikePlayer.getBallsPlayed() + 1);
                Player currentBowler = bowlingTeamPlayers.get(currentBowlerIndex);
                currentBowler.setBallsBowled(currentBowler.getBallsBowled() + 1);
                if (runsAtBall == 7) {
                    wicketFallen(currentBowler);
                } else {
                    runsScored(runsAtBall, strikePlayer, currentBowler);
                }
                if (teamAllOut() || targetAchieved()) {
                    return;
                }
            }
            swapPlayers();
            changeBowler();
        }
    }

    private void wicketFallen(Player currentBowler) {
        inning.getScoreLine().add('W');
        inning.setTotalWickets(inning.getTotalWickets() + 1);
        currentBowler.setWickets(currentBowler.getWickets() + 1);
        if (!teamAllOut()) {
            strikePlayerIndex = nextBatterIndex;
            nextBatterIndex++;
        }
    }

    private void runsScored(int runsAtBall, Player strikePlayer, Player currentBowler) {
        char runsAtBallChar = (char) ('0' + runsAtBall);
        inning.getScoreLine().add(runsAtBallChar);
        inning.setTotalRuns(inning.getTotalRuns() + runsAtBall);
        strikePlayer.setRunsScored(strikePlayer.getRunsScored() + runsAtBall);
        currentBowler.setRunsGiven(currentBowler.getRunsGiven() + runsAtBall);
        if (runsAtBall % 2 == 1) {
            swapPlayers();
        }
    }

    private void swapPlayers() {
        int temp = strikePlayerIndex;
        strikePlayerIndex = nonStrikePlayerIndex;
        nonStrikePlayerIndex = temp;
    }

    private void changeBowler() {
        if (currentBowlerIndex == 10) {
            currentBowlerIndex = 6;
        } else {
            currentBowlerIndex++;
        }
    }

    private boolean teamAllOut() {
        return (inning.getTotalWickets() == 10);
    }

    private boolean targetAchieved() {
        return (targetRuns != -1 && inning.getTotalRuns() > targetRuns);
    }

    private void saveData(Match match) {
        savePlayerStats(battingTeamPlayers, 0, match);
        savePlayerStats(bowlingTeamPlayers, 11, match);
        savePlayers(battingTeamPlayers);
        savePlayers(bowlingTeamPlayers);
        inningRepo.save(inning);
    }

    private void savePlayerStats(List<Player> players, int offset, Match match) {
        for (int i = 0; i < 11; i++) {
            playerStatService.savePlayerStat(match, (i + offset), players.get(i));
        }
    }

    private void savePlayers(List<Player> players) {
        for (Player playerInMatch : players) {
            playerService.savePlayer(playerInMatch);
        }
    }
}