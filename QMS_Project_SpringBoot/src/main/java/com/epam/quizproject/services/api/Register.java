package com.epam.quizproject.services.api;

import com.epam.quizproject.dto.UserDto;
import com.epam.quizproject.exceptions.QuizManagementException;

public interface Register {
    UserDto register(UserDto user) throws QuizManagementException;
}
