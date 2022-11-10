package com.epam.quizproject.services.api;

import com.epam.quizproject.dto.QuizDto;
import com.epam.quizproject.exceptions.QuizManagementException;

import java.util.Set;

public interface QuizService {
    int createQuiz(QuizDto newQuiz) throws QuizManagementException;
    QuizDto deleteQuiz(int quizId) throws QuizManagementException;
    boolean addQuestionToQuiz(int quizId, int questionId) throws QuizManagementException;

    Set<QuizDto> retrieveQuizzes() throws QuizManagementException;
    Set<QuizDto> retrieveQuizzesByTag(String tag) throws QuizManagementException;
    QuizDto retrieveQuizById(int quizId) throws QuizManagementException;
}
