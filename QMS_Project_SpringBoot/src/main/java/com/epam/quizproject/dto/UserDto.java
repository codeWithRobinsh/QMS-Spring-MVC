package com.epam.quizproject.dto;

import com.epam.quizproject.models.Result;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserDto {
        private int id;
        private String username;
        private String password;
        private String name;
        private String userType;

        private Set<Result> results = new HashSet<>();

        public UserDto() {
        }

        public UserDto(String username, String password, String name, String userType) {
            this(username, password, userType);
            this.name = name;
        }

        public UserDto(String username, String password, String userType) {
            this.username = username;
            this.password = password;
            this.userType = userType;
        }

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
        return "UserDto{" +
                "name='" + name + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return getUsername().equals(userDto.getUsername()) && getUserType().equals(userDto.getUserType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getUserType());
    }
}
