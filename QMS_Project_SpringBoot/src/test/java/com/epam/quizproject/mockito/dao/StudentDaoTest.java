package com.epam.quizproject.mockito.dao;

import com.epam.quizproject.dao.StudentDao;
import com.epam.quizproject.dto.ResultDto;
import com.epam.quizproject.dto.UserDto;
import com.epam.quizproject.models.Quiz;
import com.epam.quizproject.models.Result;
import com.epam.quizproject.models.User;
import com.epam.quizproject.util.Constants;
import com.epam.quizproject.util.EntityManagerFactoryCreator;
import com.epam.quizproject.util.ModelMapperClass;

import javax.persistence.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentDaoTest {
    @InjectMocks
    StudentDao studentDao;
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
    TypedQuery<Result> resultTypedQuery;

    @Mock
    Query resultQuery;

    List<User> userList;
    User user;
    Map<Integer, List<String>> map;
    Set<ResultDto> resultDtoSet;

    @BeforeEach
    void setUp() {
        user = new User(Constants.USERNAME_STUDENT, Constants.PASSWORD, Constants.USER_TYPE_STUDENT);
        userList = new ArrayList<>();
        userList.add(user);
        map = new HashMap<>();
        map.put(1, new ArrayList<>());
        resultDtoSet = new HashSet<>();
    }

    @Test
    void persistUserMethodTest() {
        UserDto userDto =  ModelMapperClass.getMapper().map(user, UserDto.class);
        when(emf.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        doNothing().when(entityManager).persist(user);
        ArgumentCaptor<User> acUser = ArgumentCaptor.forClass(User.class);
        studentDao.persistUser(userDto);
        verify(entityManager).persist(acUser.capture());
        assertEquals(user, acUser.getValue());

    }

    @Test
    void fetchStudentMethodTest() {
        UserDto userDto = ModelMapperClass.getMapper().map(user, UserDto.class);
        when(emf.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.createQuery("SELECT user FROM User user", User.class)).thenReturn(userTypedQuery);
        when(userTypedQuery.getResultList()).thenReturn(userList);
        assertEquals(Optional.of(user), studentDao.fetchStudent(userDto));
        assertEquals(Optional.empty(), studentDao.fetchStudent(new UserDto()));

    }

    @Test
    void persistPlayedQuizMethodTest() {
        UserDto newStudent = new UserDto(Constants.USERNAME_STUDENT_3, Constants.PASSWORD, Constants.USER_TYPE_STUDENT);
        User user = ModelMapperClass.getMapper().map(newStudent, User.class);
        user.setId(5);

        Quiz quiz = new Quiz(Constants.JAVA_TAG, Constants.EASY);
        quiz.setId(1);

        double score = 0;
        List<String> submittedAnswers = map.get(1);
        Result result = new Result(user, quiz, submittedAnswers);
        result.setMarksScored(score);

        when(emf.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.find(Quiz.class, 1)).thenReturn(quiz);
        when(entityManager.find(User.class, 5)).thenReturn(user);

        doNothing().when(entityManager).persist(result);

        ArgumentCaptor<Result> acResult = ArgumentCaptor.forClass(Result.class);

        studentDao.persistPlayedQuiz(map, user);
        verify(entityManager).persist(acResult.capture());
        assertEquals(result, acResult.getValue());


    }

    @Test
    void getResultMethodTest(){
        UserDto newStudent = new UserDto(Constants.USERNAME_STUDENT_3, Constants.PASSWORD, Constants.USER_TYPE_STUDENT);
        User user = ModelMapperClass.getMapper().map(newStudent, User.class);
        user.setId(5);

        Quiz quiz = new Quiz(Constants.JAVA_TAG, Constants.EASY);
        quiz.setId(1);

        double score = 0;
        List<String> submittedAnswers = map.get(1);
        Result result = new Result(user, quiz, submittedAnswers);
        result.setMarksScored(score);
        ResultDto resultDto = ModelMapperClass.getMapper().map(result, ResultDto.class);
        Set<ResultDto> resultDtos = new HashSet<>();
        resultDtos.add(resultDto);

        List<Result> resultDtoList = resultDtos.stream().map(resultDto1 -> ModelMapperClass.getMapper().map(resultDto1, Result.class)).toList();

        when(emf.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.find(User.class, 5)).thenReturn(user);
        when(entityManager.createNativeQuery("SELECT * FROM Result r JOIN User s ON r.student_id = s.id where s.id=:studentId", Result.class)).thenReturn(resultQuery);
        when(resultQuery.setParameter("studentId", 5)).thenReturn(resultQuery);
        when(resultQuery.getResultList()).thenReturn(resultDtoList);
        assertNotEquals(resultDtos, studentDao.getResult(user));


    }


}
