package com.example.GameOfCricketSpring.service;

import com.example.GameOfCricketSpring.enums.PlayerType;
import com.example.GameOfCricketSpring.model.Player;
import com.example.GameOfCricketSpring.repository.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepo playerRepo;

    public Player findById(String id) {
        return playerRepo.findById(id).get();
    }

    public Player addPlayer(String playerName, PlayerType playerType) {
        Player player = new Player(playerName, playerType);
        player.setId(UUID.randomUUID().toString());
        return playerRepo.save(player);
    }

    public Player updatePlayer(Player player) {
        return playerRepo.save(player);
    }

    public Player getDefaultPlayer(String id) {
        Player player = findById(id);
        player.setRunsScored(0);
        player.setRunsGiven(0);
        player.setWickets(0);
        player.setBallsPlayed(0);
        player.setBallsBowled(0);
        return player;
    }

    public void cleanAll() {
        List<Player> playerList = playerRepo.findAll();
        for (Player player : playerList) {
            player = getDefaultPlayer(player.getId());
            updatePlayer(player);
        }
    }

    public void savePlayer(Player playerInMatch) {
        Player playerOverall = findById(playerInMatch.getId());
        playerOverall.setRunsScored(playerOverall.getRunsScored() + playerInMatch.getRunsScored());
        playerOverall.setRunsGiven(playerOverall.getRunsGiven() + playerInMatch.getRunsGiven());
        playerOverall.setWickets(playerOverall.getWickets() + playerInMatch.getWickets());
        playerOverall.setBallsPlayed(playerOverall.getBallsPlayed() + playerInMatch.getBallsPlayed());
        playerOverall.setBallsBowled(playerOverall.getBallsBowled() + playerInMatch.getBallsBowled());
        updatePlayer(playerOverall);
    }
}