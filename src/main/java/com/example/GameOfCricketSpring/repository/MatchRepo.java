package com.example.GameOfCricketSpring.repository;

import com.example.GameOfCricketSpring.model.Match;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepo extends MongoRepository<Match, String> {
}