package com.example.GameOfCricketSpring.repository;

import com.example.GameOfCricketSpring.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepo extends MongoRepository<Player, String> {
}