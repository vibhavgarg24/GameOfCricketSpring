package com.example.GameOfCricketSpring.service;

import com.example.GameOfCricketSpring.model.Match;
import com.example.GameOfCricketSpring.model.Player;
import com.example.GameOfCricketSpring.model.PlayerStat;
import com.example.GameOfCricketSpring.repository.PlayerStatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlayerStatService {

    @Autowired
    private PlayerStatRepo playerStatRepo;

    public PlayerStat findById(String id) {
        return playerStatRepo.findById(id).get();
    }

    public PlayerStat findByPlayerIdInList(List<String> playerStatIds, String playerId) {
        for (String playerStatId : playerStatIds) {
            PlayerStat playerStat = findById(playerStatId);
            if (playerStat.getPlayerId().equals(playerId)) {
                return playerStat;
            }
        }
        return null;
    }

    public void savePlayerStat(Match match, int index, Player player) {
        List<String> playerStatIds = match.getPlayerStatIds();
        PlayerStat playerStat;
        String playerStatId;
        if (playerStatIds.size() <= index) {
            playerStat = new PlayerStat(player);
            playerStatId = UUID.randomUUID().toString();
            playerStat.setId(playerStatId);
            playerStatIds.add(playerStatId);
            match.setPlayerStatIds(playerStatIds);
        } else {
            playerStat = findByPlayerIdInList(match.getPlayerStatIds(), player.getId());
            playerStat.setRunsScored(playerStat.getRunsScored() + player.getRunsScored());
            playerStat.setRunsGiven(playerStat.getRunsGiven() + player.getRunsGiven());
            playerStat.setWickets(playerStat.getWickets() + player.getWickets());
            playerStat.setBallsPlayed(playerStat.getBallsPlayed() + player.getBallsPlayed());
            playerStat.setBallsBowled(playerStat.getBallsBowled() + player.getBallsBowled());
        }
        playerStatRepo.save(playerStat);
    }
}