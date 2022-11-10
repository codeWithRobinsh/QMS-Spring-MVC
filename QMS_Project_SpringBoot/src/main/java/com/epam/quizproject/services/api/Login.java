package com.epam.quizproject.services.api;

import com.epam.quizproject.dto.UserDto;
import com.epam.quizproject.models.User;

import java.util.Optional;

public interface Login {
    Optional<User> login(UserDto user);
}
