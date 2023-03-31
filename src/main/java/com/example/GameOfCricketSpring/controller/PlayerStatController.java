package com.example.GameOfCricketSpring.controller;

import com.example.GameOfCricketSpring.model.Match;
import com.example.GameOfCricketSpring.model.PlayerStat;
import com.example.GameOfCricketSpring.service.MatchService;
import com.example.GameOfCricketSpring.service.PlayerStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("playerStat")
public class PlayerStatController {

    @Autowired
    private PlayerStatService playerStatService;
    @Autowired
    private MatchService matchService;

    @GetMapping("")
    public PlayerStat findByMatchIdAndPlayerId(@RequestParam String matchId, @RequestParam String playerId) {
        Match match = matchService.findById(matchId);
        return playerStatService.findByPlayerIdInList(match.getPlayerStatIds(), playerId);
    }
}
