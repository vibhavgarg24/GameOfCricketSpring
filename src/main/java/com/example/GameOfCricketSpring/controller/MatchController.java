package com.example.GameOfCricketSpring.controller;

import com.example.GameOfCricketSpring.model.Match;
import com.example.GameOfCricketSpring.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("match")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping("/{id}")
    public Match findById(@PathVariable String id) {
        return matchService.findById(id);
    }

    @PostMapping("/start")
    public Match startMatch(@RequestParam String t1Name, @RequestParam String t2Name, @RequestParam int overs) {
        return matchService.start(t1Name, t2Name, overs);
    }

}