package com.example.GameOfCricketSpring.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "team")
@NoArgsConstructor
@Getter
@Setter
public class Team {

    private String id;
    private String name;
    private List<String> playerIds = new ArrayList<>(11);

    public Team(String name) {
        this.name = name;
    }
}