package com.example.demo.Problems;

import org.springframework.web.multipart.MultipartFile;

public class ProblemsDTO {
    private String statement;
    private String title;
    private MultipartFile solution;
    private double score;
    private int numOfTestCases;

    private String category;
    private String difficulty;

    public ProblemsDTO(String statement, String title, MultipartFile solution, double score, int numOfTestCases,  String category, String difficulty) {
        this.statement = statement;
        this.title = title;
        this.solution = solution;
        this.score = score;
        this.numOfTestCases = numOfTestCases;

        this.category = category;
        this.difficulty = difficulty;
    }

    public ProblemsDTO() {
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MultipartFile getSolution() {
        return solution;
    }

    public void setSolution(MultipartFile solution) {
        this.solution = solution;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getNumOfTestCases() {
        return numOfTestCases;
    }

    public void setNumOfTestCases(int numOfTestCases) {
        this.numOfTestCases = numOfTestCases;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

}
