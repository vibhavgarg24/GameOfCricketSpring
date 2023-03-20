package com.example.GameOfCricketSpring.service;

import com.example.GameOfCricketSpring.model.Inning;
import com.example.GameOfCricketSpring.model.Match;
import com.example.GameOfCricketSpring.repository.MatchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MatchService {

    @Autowired
    private MatchRepo matchRepo;
    @Autowired
    private InningService inningService;
    @Autowired
    private TeamService teamService;
    private Match match;

    public Match findById(String id) {
        return matchRepo.findById(id).get();
    }

    public Match start(String t1Name, String t2Name, int overs) {
        config(t1Name, t2Name, overs);
        Inning firstInning = inningService.simulateInning(match.getFirstInningId(), -1, match);
        Inning secondInning = inningService.simulateInning(match.getSecondInningId(), firstInning.getTotalRuns(), match);
        findWinner(firstInning, secondInning);
        return saveMatch();
    }

    private void config(String t1Name, String t2Name, int overs) {
        match = new Match();
        match.setOvers(overs);
        String t1Id = teamService.findByName(t1Name).getId();
        String t2Id = teamService.findByName(t2Name).getId();
        int tossValue = toss(t1Name, t2Name);
        String firstInningId;
        String secondInningId;
        if (tossValue < 2) {
            firstInningId = inningService.addInning(t1Id, t2Id).getId();
            secondInningId = inningService.addInning(t2Id, t1Id).getId();
        } else {
            firstInningId = inningService.addInning(t2Id, t1Id).getId();
            secondInningId = inningService.addInning(t1Id, t2Id).getId();
        }
        match.setFirstInningId(firstInningId);
        match.setSecondInningId(secondInningId);
    }

    private int toss(String t1Name, String t2Name) {
        int tossValue = (int) (Math.random() * 4);
        String tossString = "";
        switch (tossValue) {
            case 0:
                tossString = t1Name + " won and chose Batting.";
                break;
            case 1:
                tossString = t2Name + " won and chose Bowling.";
                break;
            case 2:
                tossString = t1Name + " won and chose Bowling.";
                break;
            case 3:
                tossString = t2Name + " won and chose Batting.";
        }
        match.setToss(tossString);
        return tossValue;
    }

    private void findWinner(Inning firstInning, Inning secondInning) {
        String winnerId = null;
        if (firstInning.getTotalRuns() > secondInning.getTotalRuns()) {
            winnerId = firstInning.getBattingTeamId();
        } else if (firstInning.getTotalRuns() < secondInning.getTotalRuns()) {
            winnerId = secondInning.getBattingTeamId();
        }
        match.setWinnerId(winnerId);
    }

    private Match saveMatch() {
        match.setId(UUID.randomUUID().toString());
        return matchRepo.save(match);
    }
}