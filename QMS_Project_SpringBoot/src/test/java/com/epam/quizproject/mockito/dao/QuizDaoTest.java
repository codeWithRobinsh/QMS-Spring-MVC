package com.epam.quizproject.mockito.dao;

import com.epam.quizproject.dao.AdminDao;
import com.epam.quizproject.dao.QuizDao;
import com.epam.quizproject.dto.QuizDto;
import com.epam.quizproject.exceptions.QuizManagementException;
import com.epam.quizproject.models.Question;
import com.epam.quizproject.models.Quiz;
import com.epam.quizproject.models.User;
import com.epam.quizproject.util.Constants;
import com.epam.quizproject.util.EntityManagerFactoryCreator;
import com.epam.quizproject.util.ModelMapperClass;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
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
class QuizDaoTest {
    @InjectMocks
    QuizDao quizDao;
    @Mock
    EntityManagerFactoryCreator entityManagerFactoryCreator;
    @Mock
    EntityManagerFactory emf;
    @Mock
    EntityManager entityManager;
    @Mock
    EntityTransaction entityTransaction;
    @Mock
    TypedQuery<User> userTypedQuery;
    @Mock
    TypedQuery<Quiz> quizTypedQuery;

    @Test
    void persistCreatedQuizMethodTest() {
        QuizDto quizDto = new QuizDto(Constants.JAVA_TAG, Constants.EASY);
        quizDto.setId(1);
        Quiz quiz = ModelMapperClass.getMapper().map(quizDto, Quiz.class);
        quiz.setId(1);

        when(emf.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        doNothing().when(entityManager).persist(quiz);

        assertEquals(1, quizDao.persistCreatedQuiz(quizDto));
        assertNotEquals(-1, quizDao.persistCreatedQuiz(quizDto));

    }

    @Test
    void persistAddQuestionToQuizMethodTest() throws QuizManagementException {
        Quiz quiz = new Quiz(Constants.JAVA_TAG, Constants.EASY);
        quiz.setId(1);

        Question question = new Question();
        question.setId(1);

        when(emf.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.find(Quiz.class, 1)).thenReturn(quiz);
        when(entityManager.find(Question.class, 1)).thenReturn(question);
        doNothing().when(entityManager).persist(quiz);
        doNothing().when(entityManager).persist(question);
        assertTrue(quizDao.persistAddQuestionToQuiz(1, 1));

        when(entityManager.find(Quiz.class, -1)).thenReturn(null);
        when(entityManager.find(Question.class, -1)).thenReturn(null);
        assertThrows(QuizManagementException.class, () -> quizDao.persistAddQuestionToQuiz(-1, -1));
    }

    @Test
    void deleteQuizMethodTest() throws QuizManagementException {
        QuizDto quizDto = new QuizDto(Constants.JAVA_TAG, Constants.EASY);
        quizDto.setId(1);
        Quiz quiz = ModelMapperClass.getMapper().map(quizDto, Quiz.class);
        quiz.setId(1);

        when(emf.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.find(Quiz.class, 1)).thenReturn(quiz);
        doNothing().when(entityManager).remove(quiz);
        assertEquals(quizDto, quizDao.deleteQuiz(1));

        when(entityManager.find(Quiz.class, -1)).thenReturn(null);
        assertThrows(QuizManagementException.class, () -> quizDao.deleteQuiz(-1));
    }
    @Test
    void getQuizzesMethodTest() {
        QuizDto quizDto = new QuizDto(Constants.JAVA_TAG, Constants.EASY);
        quizDto.setId(1);
        Quiz quiz = ModelMapperClass.getMapper().map(quizDto, Quiz.class);
        quiz.setId(1);
        Set<QuizDto> quizDtoSet = new HashSet<>();
        quizDtoSet.add(quizDto);
        Set<Quiz> quizzes = new HashSet<>();
        quizzes.add(quiz);

        when(emf.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.createQuery(Constants.RETRIEVE_QUIZZES_QUERY, Quiz.class)).thenReturn(quizTypedQuery);
        when(quizTypedQuery.getResultList()).thenReturn(quizzes.stream().toList());

        assertEquals(quizDtoSet, quizDao.getQuizzes());

    }


}
