package com.example.demo.Classes;

import com.example.demo.Posts.Posts;
import com.example.demo.Users.Users;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private long ownerId;
    private Date createdAt;
    private String classCode;

    @ManyToMany
    private List<Users> students;

    @ManyToMany
    private List<Users> teachers;

    @OneToMany
    private List<Posts> posts;

    public Classes() {
    }

    public Classes(long id, String name, String description, long ownerId, Date createdAt, List<Users> students, List<Users> teachers, List<Posts> posts) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
        this.students = students;
        this.teachers = teachers;
        this.posts = posts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public List<Users> getStudents() {
        return students;
    }

    public void setStudents(List<Users> students) {
        this.students = students;
    }

    public List<Users> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Users> teachers) {
        this.teachers = teachers;
    }

    public List<Posts> getPosts() {
        return posts;
    }

    public void setPosts(List<Posts> posts) {
        this.posts = posts;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }
}
