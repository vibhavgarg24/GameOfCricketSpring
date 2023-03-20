package com.example.GameOfCricketSpring.controller;

import com.example.GameOfCricketSpring.enums.PlayerType;
import com.example.GameOfCricketSpring.model.Player;
import com.example.GameOfCricketSpring.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/{id}")
    public Player findById(@PathVariable String id) {
        return playerService.findById(id);
    }

    @PostMapping("")
    public Player addPlayer(@RequestParam String playerName, @RequestParam PlayerType playerType) {
        return playerService.addPlayer(playerName, playerType);
    }

    @PutMapping("")
    public Player updateTeam(@RequestBody Player player) {
        return playerService.updatePlayer(player);
    }

    @DeleteMapping("/cleanAll")
    public void cleanAll() {
        playerService.cleanAll();
    }
}