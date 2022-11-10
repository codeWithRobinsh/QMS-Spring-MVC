package com.epam.quizproject.models;

import javax.persistence.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String name;
    private String userType;


    @OneToMany(mappedBy = "student")
    private Set<Result> results = new HashSet<>();

    public User() {
    }

    public User(String username, String password, String name, String userType) {
        this(username, password, userType);
        this.name = name;
    }

    public User(String username, String password, String userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public static final Logger LOGGER = LogManager.getLogger(User.class);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


    public Set<Result> getResults() {
        return results;
    }

    public void setResults(Set<Result> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        LOGGER.info("---------------------------------------------------------------------------------------------");
        LOGGER.info(String.format("%5s %10s %10s %8s %20s %17s", "EMPLOYEE ID", "NAME", "GENDER", "AGE", "E-MAIL ID", "SALARY"));
        LOGGER.info("");
        LOGGER.info("---------------------------------------------------------------------------------------------");


        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getUsername().equals(user.getUsername()) && getUserType().equals(user.getUserType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getUserType());
    }
}
