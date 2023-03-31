package com.example.GameOfCricketSpring.repository;

import com.example.GameOfCricketSpring.model.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepo extends MongoRepository<Team, String> {

    Team findByName(String name);
}