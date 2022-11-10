package com.epam.quizproject.services;

import com.epam.quizproject.dao.QuestionDao;
import com.epam.quizproject.dto.QuestionDto;
import com.epam.quizproject.exceptions.QuizManagementException;
import com.epam.quizproject.models.Question;
import com.epam.quizproject.services.api.QuestionService;
import com.epam.quizproject.util.Constants;
import com.epam.quizproject.util.ModelMapperClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionDao questionDao;
    private static QuestionService questionService;
    private QuestionServiceImpl(){

    }
    public static QuestionService getInstance(){
        if (questionService == null)
            questionService = new QuestionServiceImpl();
        return questionService;
    }

    @Override
    public boolean createQuestion(Question question) throws QuizManagementException {
        Set<QuestionDto> questionDtoSet = questionDao.retrieveQuestions();
        QuestionDto questionDto = ModelMapperClass.getMapper().map(question, QuestionDto.class);
        if (questionDtoSet.contains(questionDto))
            throw new QuizManagementException(Constants.QUESTION_ALREADY_PRESENT);
        questionDao.persistCreatedQuestion(question);
        return true;
    }

    @Override
    public Set<QuestionDto> retrieveQuestions() throws QuizManagementException {
        Set<QuestionDto> questionDtoSet = questionDao.retrieveQuestions();
        if (questionDtoSet.isEmpty())
            throw new QuizManagementException(Constants.NO_QUESTION_EXISTS);
        return questionDtoSet;
    }

    @Override
    public Set<QuestionDto> retrieveQuestionsByTag(String tag) throws QuizManagementException {
        Set<QuestionDto> questionDtoSet = questionDao.retrieveQuestions();
        if (!questionDtoSet.isEmpty())
            questionDtoSet = questionDtoSet.stream().filter(questionDto -> questionDto.getTag().equalsIgnoreCase(tag)).collect(Collectors.toSet());
        if (questionDtoSet.isEmpty())
            throw new QuizManagementException(Constants.NO_QUESTION_EXISTS);
        return questionDtoSet;
    }
}
