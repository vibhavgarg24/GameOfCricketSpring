package com.example.GameOfCricketSpring.controller;

import com.example.GameOfCricketSpring.model.Team;
import com.example.GameOfCricketSpring.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/{id}")
    public Team findById(@PathVariable String id) {
        return teamService.findById(id);
    }

    @PostMapping("")
    public Team addTeam(@RequestParam String teamName) {
        return teamService.addTeam(teamName);
    }

    @PutMapping("")
    public Team updateTeam(@RequestBody Team team) {
        return teamService.updateTeam(team);
    }
}