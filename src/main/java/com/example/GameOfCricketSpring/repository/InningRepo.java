package com.example.GameOfCricketSpring.repository;

import com.example.GameOfCricketSpring.model.Inning;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InningRepo extends MongoRepository<Inning, String> {
}