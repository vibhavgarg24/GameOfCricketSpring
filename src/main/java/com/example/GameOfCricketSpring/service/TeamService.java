package com.example.GameOfCricketSpring.service;

import com.example.GameOfCricketSpring.model.Team;
import com.example.GameOfCricketSpring.repository.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TeamService {

    @Autowired
    private TeamRepo teamRepo;
    @Autowired
    private PlayerService playerService;

    public Team findById(String id) {
        return teamRepo.findById(id).get();
    }

    public Team findByName(String name) {
        return teamRepo.findByName(name);
    }

    public Team addTeam(String teamName) {
        Team team = new Team(teamName);
        team.setId(UUID.randomUUID().toString());
        return teamRepo.save(team);
    }

    public Team updateTeam(Team team) {
        return teamRepo.save(team);
    }
}