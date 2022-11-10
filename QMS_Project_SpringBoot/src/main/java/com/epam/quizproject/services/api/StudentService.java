package com.epam.quizproject.services.api;

import com.epam.quizproject.dto.ResultDto;
import com.epam.quizproject.exceptions.QuizManagementException;
import com.epam.quizproject.models.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface StudentService {
    Register registerStudent();
    Login loginStudent();
    void playQuiz(Map<Integer, List<String>> map, User student);
    Set<ResultDto> retrieveAttemptedQuizzes(User student) throws QuizManagementException;
}
