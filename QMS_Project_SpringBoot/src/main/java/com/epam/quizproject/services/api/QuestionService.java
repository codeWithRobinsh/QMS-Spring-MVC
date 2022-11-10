package com.epam.quizproject.services.api;

import com.epam.quizproject.dto.QuestionDto;
import com.epam.quizproject.exceptions.QuizManagementException;
import com.epam.quizproject.models.Question;

import java.util.Set;

public interface QuestionService {
    boolean createQuestion(Question newQuestion) throws QuizManagementException;
//    boolean modifyQuestion(QuestionDto existingQuestion);
//    boolean deleteQuestion(QuestionDto questionDto);
//
    Set<QuestionDto> retrieveQuestions() throws QuizManagementException;
    Set<QuestionDto> retrieveQuestionsByTag(String tag) throws QuizManagementException;

}
