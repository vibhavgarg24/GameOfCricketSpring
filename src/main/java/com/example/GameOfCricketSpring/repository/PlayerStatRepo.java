package com.example.GameOfCricketSpring.repository;

import com.example.GameOfCricketSpring.model.PlayerStat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerStatRepo extends MongoRepository<PlayerStat, String> {

    PlayerStat findByPlayerId(String playerId);
}
