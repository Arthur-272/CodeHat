package com.example.demo.TestCases;

import com.example.demo.Problems.Problems;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TestCases {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String input;
    private String output;

    @ManyToOne
    @JsonIgnore
    private Problems problem;

}
