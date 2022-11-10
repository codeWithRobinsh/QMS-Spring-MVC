package com.epam.quizproject.mockito.dao;

import com.epam.quizproject.dao.QuestionDao;
import com.epam.quizproject.dto.QuestionDto;
import com.epam.quizproject.models.Options;
import com.epam.quizproject.models.Question;
import com.epam.quizproject.models.Result;
import com.epam.quizproject.util.Constants;
import com.epam.quizproject.util.EntityManagerFactoryCreator;
import com.epam.quizproject.util.ModelMapperClass;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionDaoTest {
    @InjectMocks
    private QuestionDao questionDao;
    @Mock
    private EntityManagerFactoryCreator entityManagerFactoryCreator;
    @Mock
    private EntityManagerFactory emf;
    @Mock
    private EntityManager entityManager;
    @Mock
    private EntityTransaction entityTransaction;
    @Mock
    private TypedQuery<Question> questionTypedQuery;

    Question question;
    Question newQuestion;
    Set<QuestionDto> questionDtoSet;
    Set<Question> questions;

    @BeforeEach
    void setUp() {
        question = new Question();
        question.setId(1);
        question.setTag(Constants.JAVA_TAG);
        question.setOptions(new Options());
        question.setDifficultyLevel(Constants.EASY);
        question.setContent(Constants.DUMMY_CONTENT);
        question.setAnswer(Constants.DUMMY_ANSWER);

        newQuestion = new Question();
        question.setId(2);
        newQuestion.setTag(Constants.JAVA_TAG);
        newQuestion.setOptions(new Options());
        newQuestion.setDifficultyLevel(Constants.EASY);
        newQuestion.setContent(Constants.DUMMY_CONTENT);
        newQuestion.setAnswer(Constants.DUMMY_ANSWER);

        questions = new HashSet<>();
        questions.add(question);
        questions.add(newQuestion);

        questionDtoSet = new HashSet<>(5);
        questionDtoSet.add(ModelMapperClass.getMapper().map(question, QuestionDto.class));
        questionDtoSet.add(ModelMapperClass.getMapper().map(newQuestion, QuestionDto.class));
    }

    @Test
    void persistCreatedQuestionMethodTest() {
        when(emf.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        doNothing().when(entityManager).persist(question);
        ArgumentCaptor<Question> acQuestion = ArgumentCaptor.forClass(Question.class);
        questionDao.persistCreatedQuestion(question);
        verify(entityManager).persist(acQuestion.capture());
        assertEquals(question, acQuestion.getValue());
    }

    @Test
    void retrieveQuestionsMethodTest() {
        when(emf.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.createQuery(Constants.RETRIEVE_QUESTIONS_QUERY, Question.class)).thenReturn(questionTypedQuery);
        when(questionTypedQuery.getResultList()).thenReturn(questions.stream().toList());
        assertEquals(questionDtoSet, questionDao.retrieveQuestions());
    }


}
