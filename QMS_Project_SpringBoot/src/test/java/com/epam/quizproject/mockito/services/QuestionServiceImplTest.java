package com.epam.quizproject.mockito.services;

import com.epam.quizproject.dao.QuestionDao;
import com.epam.quizproject.dto.QuestionDto;
import com.epam.quizproject.exceptions.QuizManagementException;
import com.epam.quizproject.models.Options;
import com.epam.quizproject.models.Question;
import com.epam.quizproject.services.QuestionServiceImpl;
import com.epam.quizproject.util.Constants;
import com.epam.quizproject.util.ModelMapperClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {
    @InjectMocks
    private QuestionServiceImpl questionService;
    @Mock
    private QuestionDao questionDao;

    QuestionDto newQuestionDto;

    Question question;

    Set<QuestionDto> questionDtoSet;

    @BeforeEach
    void setUp(){
        Options option = new Options();
        option.setOptionA("aa");
        option.setOptionB("bb");
        option.setOptionC("cc");
        option.setOptionD("dd");
        question = new Question(Constants.DUMMY_TAG, Constants.DUMMY_DIFFICULTY_LEVEL, Constants.MARKS, Constants.DUMMY_CONTENT, Constants.DUMMY_ANSWER, option);
        question.setId(Constants.QUESTION_ID_0);
        option.setQuestion(question);
        newQuestionDto = ModelMapperClass.getMapper().map(question, QuestionDto.class);
        questionDtoSet = new HashSet<>();
        QuestionServiceImpl.getInstance();
    }

    @Test
    void createQuestionMethodTest() throws QuizManagementException {
       doNothing().when(questionDao).persistCreatedQuestion(question);
       QuestionDao.getInstance(questionDao);
       assertTrue(questionService.createQuestion(question));

       questionDtoSet.add(newQuestionDto);
       when(questionDao.retrieveQuestions()).thenReturn(questionDtoSet);
       assertThrows(QuizManagementException.class, () -> questionService.createQuestion(question));
    }

    @Test
    void retrieveQuestionsMethodTest() throws QuizManagementException {
        when(questionDao.retrieveQuestions()).thenReturn(questionDtoSet);
        questionDtoSet.add(newQuestionDto);
        assertEquals(questionDtoSet, questionService.retrieveQuestions());
        questionDtoSet.remove(newQuestionDto);
        assertThrows(QuizManagementException.class, () -> questionService.retrieveQuestions());
    }

    @Test
    void retrieveQuestionsByTagMethodTest() throws QuizManagementException {
        when(questionDao.retrieveQuestions()).thenReturn(questionDtoSet);
        questionDtoSet.add(newQuestionDto);
        assertEquals(questionDtoSet, questionService.retrieveQuestionsByTag(Constants.DUMMY_TAG));
        assertThrows(QuizManagementException.class, () -> questionService.retrieveQuestionsByTag(Constants.JAVA_TAG));
    }

}
