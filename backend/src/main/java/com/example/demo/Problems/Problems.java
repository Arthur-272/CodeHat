package com.example.demo.Problems;

import com.example.demo.TestCases.TestCases;
import com.example.demo.Users.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Problems {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Lob
    private String statement;
    private String title;
    @Lob
    @JsonIgnore
    private byte[] solution;
    private double score;
    private int numOfTestCases;
//    @Lob
//    @JsonIgnore
//    private byte[] testCasesFile;
    @OneToMany
    private List<TestCases> testCases = new ArrayList<>();

    private String category;
    private String difficulty;
    private Date problemDate;

    @ManyToOne
    private Users author;

    public Problems() {}

    public Problems(String statement, String title, byte[] solution, double score, int numOfTestCases,  String category, String difficulty) {
        this.statement = statement;
        this.title = title;
        this.solution = solution;
        this.score = score;
        this.numOfTestCases = numOfTestCases;
        this.category = category;
        this.difficulty = difficulty;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String problemStatement) {
        this.statement = problemStatement;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String problemTitle) {
        this.title = problemTitle;
    }

    public byte[] getSolution() {
        return solution;
    }

    public void setSolution(byte[] problemSolution) {
        this.solution = problemSolution;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double problemScore) {
        this.score = problemScore;
    }

    public int getNumOfTestCases() {
        return numOfTestCases;
    }

    public void setNumOfTestCases(int numOfTestCases) {
        this.numOfTestCases = numOfTestCases;
    }

    public List<TestCases> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCases> testCases) {
        this.testCases = testCases;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String problemCategory) {
        this.category = problemCategory;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String problemDifficulty) {
        this.difficulty = problemDifficulty;
    }

    public Date getProblemDate() {
        return problemDate;
    }

    public void setProblemDate(Date problemDate) {
        this.problemDate = problemDate;
    }

    public Users getAuthor() {
        return author;
    }

    public void setAuthor(Users author) {
        this.author = author;
    }
}
