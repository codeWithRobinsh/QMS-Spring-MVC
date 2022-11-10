package com.epam.quizproject.mockito.services;

import com.epam.quizproject.dao.AdminDao;
import com.epam.quizproject.dao.StudentDao;
import com.epam.quizproject.dto.ResultDto;
import com.epam.quizproject.dto.UserDto;
import com.epam.quizproject.exceptions.QuizManagementException;
import com.epam.quizproject.models.User;
import com.epam.quizproject.services.Authentication;
import com.epam.quizproject.services.StudentServiceImpl;
import com.epam.quizproject.services.api.Login;
import com.epam.quizproject.services.api.Register;
import com.epam.quizproject.util.Constants;
import com.epam.quizproject.util.ModelMapperClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @InjectMocks
    private StudentServiceImpl studentServiceImpl;
    @Mock
    private StudentDao studentDao;
    @Mock
    private AdminDao adminDao;

    @Mock
    private Authentication authentication;

    @Mock
    private Register register;
    @Mock
    private Login login;

    Map<Integer, List<String>> map;
    Set<ResultDto> resultDtoSet;

    @BeforeEach
    void setUp(){
        map = new HashMap<>();
        map.put(1, new ArrayList<>());
        resultDtoSet = new HashSet<>();
    }


    @Test
    void registerStudentMethodTest() throws QuizManagementException {
        UserDto newStudent = new UserDto(Constants.USERNAME_STUDENT_3, Constants.PASSWORD, Constants.USER_TYPE_STUDENT);
        doNothing().when(studentDao).persistUser(newStudent);
        StudentDao.getInstance(studentDao);
        assertEquals(newStudent, studentServiceImpl.registerStudent().register(newStudent));

    }

    @Test
    void loginStudentMethodTest(){
        UserDto newStudent = new UserDto(Constants.USERNAME_STUDENT_3, Constants.PASSWORD, Constants.USER_TYPE_STUDENT);
        User user = ModelMapperClass.getMapper().map(newStudent, User.class);
        Optional<User> optionalUser = Optional.of(user);
        when(studentDao.fetchStudent(newStudent)).thenReturn(optionalUser);
        when(authentication.authenticate(user)).thenReturn(true);
        StudentDao.getInstance(studentDao);
        assertEquals(optionalUser, studentServiceImpl.loginStudent().login(newStudent));
        assertEquals(Optional.empty(), studentServiceImpl.loginStudent().login(new UserDto()));
    }

    @Test
    void playQuizMethodTest(){
        UserDto newStudent = new UserDto(Constants.USERNAME_STUDENT_3, Constants.PASSWORD, Constants.USER_TYPE_STUDENT);
        User user = ModelMapperClass.getMapper().map(newStudent, User.class);
        doNothing().when(studentDao).persistPlayedQuiz(map, user);
        StudentDao.getInstance(studentDao);
        studentServiceImpl.playQuiz(map, user);
        verify(studentDao, times(1)).persistPlayedQuiz(map, user);
    }

    @Test
    void retrieveAttemptedQuizzesMethodTest(){
        StudentServiceImpl.getInstance();
        UserDto newStudent = new UserDto(Constants.USERNAME_STUDENT_3, Constants.PASSWORD, Constants.USER_TYPE_STUDENT);
        User user = ModelMapperClass.getMapper().map(newStudent, User.class);
        when(studentDao.getResult(user)).thenReturn(resultDtoSet);
        assertThrows(QuizManagementException.class, () -> studentServiceImpl.retrieveAttemptedQuizzes(user));

    }




}
