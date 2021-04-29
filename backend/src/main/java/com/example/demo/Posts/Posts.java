package com.example.demo.Posts;

import com.example.demo.Classes.Classes;
import com.example.demo.Comments.Comments;
import com.example.demo.Problems.Problems;
import com.example.demo.Users.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private Date dateAdded;

    @ManyToOne
    private Users owner;

    @OneToMany
    List<Problems> problems;

    @ManyToMany
    private List<Users> usersConcerning;

    @OneToMany
    private List<Comments> comments;

    @ManyToOne
    @JsonIgnore
    private Classes classes;

    public Posts() {

    }

    public Posts(long id, String title, String description, Date dateAdded, Users owner, List<Problems> problems,List<Users> usersConcerning, List<Comments> comments, Classes classes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateAdded = dateAdded;
        this.owner = owner;
        this.problems = problems;
        this.usersConcerning = usersConcerning;
        this.comments = comments;
        this.classes = classes;
    }

    @JsonIgnore
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    @JsonIgnore
    public List<Users> getUsersConcerning() {
        return usersConcerning;
    }

    public void setUsersConcerning(List<Users> usersConcerning) {
        this.usersConcerning = usersConcerning;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public List<Problems> getProblems() {
        return problems;
    }

    public void setProblems(List<Problems> problems) {
        this.problems = problems;
    }
}
