package com.example.GameOfCricketSpring.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "match")
@NoArgsConstructor
@Getter
@Setter
public class Match {

    private String id;
    private int overs;
    private String toss;
    private String firstInningId;
    private String secondInningId;
    private String winnerId;
    private List<String> playerStatIds = new ArrayList<>(22);
}