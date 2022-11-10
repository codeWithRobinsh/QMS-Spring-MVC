package com.epam.quizproject.mockito.services;

import com.epam.quizproject.dao.QuestionDao;
import com.epam.quizproject.dao.QuizDao;
import com.epam.quizproject.dto.QuizDto;
import com.epam.quizproject.exceptions.QuizManagementException;
import com.epam.quizproject.services.QuizServiceImpl;
import com.epam.quizproject.services.api.QuizService;
import com.epam.quizproject.util.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuizServiceImplTest {
    @InjectMocks
    private QuizServiceImpl quizService;

    @Mock
    private QuizDao quizDao;

    @Mock
    private QuestionDao questionDao;

    QuizDto newQuizDto;
    QuizDto existingQuizDto;
    Set<QuizDto> quizDtoSet;

    @BeforeEach
    void setUp(){
        newQuizDto = new QuizDto(Constants.JAVA_TAG, Constants.MEDIUM);
        newQuizDto.setId(0);
        quizDtoSet = new HashSet<>();
        quizDtoSet.add(newQuizDto);
        QuizServiceImpl.getInstance();
    }

    @Test
    void createQuizMethodTest() throws QuizManagementException {
        when(quizDao.persistCreatedQuiz(newQuizDto)).thenReturn(newQuizDto.getId());
        QuizDao.getInstance(quizDao);
        Assertions.assertEquals(0, quizService.createQuiz(newQuizDto));

        when(quizDao.getQuizzes()).thenReturn(quizDtoSet);
        Assertions.assertThrows(QuizManagementException.class, () -> quizService.createQuiz(newQuizDto));
    }

    @Test
    void addQuestionToQuizMethodTest() throws QuizManagementException {
        when(quizDao.persistAddQuestionToQuiz(1, 1)).thenReturn(true);
        QuizDao.getInstance();
        Assertions.assertTrue(quizDao.persistAddQuestionToQuiz(1,1));
        when(quizDao.persistAddQuestionToQuiz(2, 1)).thenThrow(QuizManagementException.class);
        Assertions.assertThrows(QuizManagementException.class, () -> quizService.addQuestionToQuiz(2, 1));
    }

    @Test
    void deleteQuizMethodTest() throws QuizManagementException {
        when(quizDao.deleteQuiz(0)).thenReturn(newQuizDto);
        QuizDao.getInstance();
        Assertions.assertEquals(newQuizDto, quizDao.deleteQuiz(0));
        when(quizDao.deleteQuiz(2)).thenThrow(QuizManagementException.class);
        Assertions.assertThrows(QuizManagementException.class, () -> quizService.deleteQuiz(2));
    }

    @Test
    void retrieveQuizzesMethodTest() throws QuizManagementException {
        when(quizDao.getQuizzes()).thenReturn(quizDtoSet);
        Assertions.assertEquals(quizDtoSet, quizService.retrieveQuizzes());

        when(quizDao.getQuizzes()).thenReturn(new HashSet<>());
        Assertions.assertThrows(QuizManagementException.class, () -> quizService.retrieveQuizzes());
    }

    @Test
    void retrieveQuizzesByTagMethodTest() throws QuizManagementException {
        when(quizDao.getQuizzes()).thenReturn(quizDtoSet);
        Assertions.assertEquals(quizDtoSet, quizService.retrieveQuizzesByTag(Constants.JAVA_TAG));
        Assertions.assertThrows(QuizManagementException.class, () -> quizService.retrieveQuizzesByTag(Constants.DUMMY_TAG));
    }

    @Test
    void retrieveQuizzesByIdMethodTest() throws QuizManagementException {
        when(quizDao.getQuizzes()).thenReturn(quizDtoSet);
        Assertions.assertEquals(newQuizDto, quizService.retrieveQuizById(0));
        Assertions.assertThrows(QuizManagementException.class, () -> quizService.retrieveQuizById(2));
    }


}
