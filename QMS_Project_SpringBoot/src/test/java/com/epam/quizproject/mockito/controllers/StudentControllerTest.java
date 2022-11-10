package com.epam.quizproject.mockito.controllers;

import com.epam.quizproject.controllers.StudentController;
import com.epam.quizproject.dto.QuizDto;
import com.epam.quizproject.dto.ResultDto;
import com.epam.quizproject.dto.UserDto;
import com.epam.quizproject.models.User;
import com.epam.quizproject.services.api.*;
import com.epam.quizproject.util.Constants;
import com.epam.quizproject.util.ModelMapperClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = StudentController.class)
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    @Autowired
    public StudentService studentService;

    @MockBean
    @Autowired
    public QuizService quizService;

    @MockBean
    @Autowired
    public QuestionService questionService;

    @MockBean
    @Autowired
    public Register register;

    @MockBean
    @Autowired
    public Login login;

    @Test
    void loadStudentDashboardMethodTest() throws Exception {
        mockMvc.perform(post("/student/studentDashboard")
                        .contentType("application/json"))
                .andExpectAll(
                        status().isOk(),
                        view().name("studentPage"),
                        forwardedUrl("/views/studentPage.jsp")
                );
    }

    @Test
    void loadRegistrationMenuMethodTest() throws Exception {
        mockMvc.perform(post("/student/registrationForm")
                        .contentType("application/json"))
                .andExpectAll(
                        status().isOk(),
                        view().name("studentRegistrationPage"),
                        forwardedUrl("/views/studentRegistrationPage.jsp")
                );
    }

    @Test
    void signUpStudentMethodTest() throws Exception {
        UserDto newStudentDto = new UserDto(Constants.DUMMY_USERNAME_STUDENT_000, Constants.PASSWORD, Constants.USER_TYPE_STUDENT);
        when(studentService.registerStudent()).thenReturn(register);
        mockMvc.perform(post("/student/registerStudent")
                        .sessionAttr("userDto", newStudentDto))
                .andExpectAll(
                        status().isOk(),
                        view().name("success"),
                        model().attributeExists(Constants.MESSAGE),
                        model().attribute(Constants.MESSAGE, "registration successful"),
                        forwardedUrl("/views/success.jsp")
                );

    }


    @Test
    void loadLoginMenuMethodTest() throws Exception {
        mockMvc.perform(post("/student/loginForm")
                        .contentType("application/json"))
                .andExpectAll(
                        status().isOk(),
                        view().name("studentLoginPage"),
                        forwardedUrl("/views/studentLoginPage.jsp")
                );
    }

    @Test
    void  signInStudentMethodTest() throws Exception {
        UserDto existingStudentDto = new UserDto(Constants.DUMMY_USERNAME_STUDENT, Constants.PASSWORD, Constants.USER_TYPE_STUDENT);
        existingStudentDto.setId(1);
        User existingUser = ModelMapperClass.getMapper().map(existingStudentDto, User.class);
        when(studentService.loginStudent()).thenReturn(login);
        when(studentService.loginStudent().login(existingStudentDto)).thenReturn(Optional.of(existingUser));
        mockMvc.perform(post("/student/loginStudent")
                        .param("username", Constants.DUMMY_USERNAME_STUDENT)
                        .param("password", Constants.PASSWORD)
                        .param("userType", Constants.USER_TYPE_STUDENT))
                .andExpectAll(
                        status().isOk(),
                        view().name("studentOperationsMenu"),
                        model().attributeExists(Constants.STUDENT, "message"),
                        model().attribute(Constants.STUDENT, existingUser),
                        model().attribute(Constants.MESSAGE, "login successful"),
                        forwardedUrl("/views/studentOperationsMenu.jsp")
                );
    }


    @Test
    void playQuizFormMethodTest() throws Exception {
        mockMvc.perform(post("/student/playQuizForm")
                        .contentType("application/json"))
                .andExpectAll(
                        status().isOk(),
                        view().name("playQuizForm"),
                        forwardedUrl("/views/playQuizForm.jsp")
                );
    }

    //testing....
    @Test
    void  playQuizMenuMethodTest() throws Exception {
        int quizId = 9;
        QuizDto newQuizDto = new QuizDto(Constants.DUMMY_TAG, Constants.MEDIUM);
        when(quizService.retrieveQuizById(quizId)).thenReturn(newQuizDto);
        mockMvc.perform(post("/student/playQuizMenu")
                        .param("quizId", String.valueOf(quizId)))
                .andExpectAll(
                        status().isOk(),
                        view().name("playQuiz"),
                        model().attributeExists("questions", "quizId"),
                        model().attribute("questions", newQuizDto.getQuestionSet()),
                        model().attribute("quizId", quizId),
                        forwardedUrl("/views/playQuiz.jsp")
                );
    }

    //testing....
    @Test
    void  getResultMethodTest() throws Exception {
        UserDto existingStudentDto = new UserDto(Constants.DUMMY_USERNAME_STUDENT, Constants.PASSWORD, Constants.USER_TYPE_STUDENT);
        existingStudentDto.setId(1);
        User existingUser = ModelMapperClass.getMapper().map(existingStudentDto, User.class);
        ResultDto resultDto = new ResultDto();
        StudentController.loggedInStudent = existingUser;
        System.out.println();
        when(studentService.retrieveAttemptedQuizzes(StudentController.loggedInStudent)).thenReturn(new HashSet<>(List.of(resultDto)));
        mockMvc.perform(post("/student/getResult"))
                .andExpectAll(
                        status().isOk(),
                        view().name("viewResult"),
                        model().attributeExists("resultDtoSet", Constants.STUDENT),
                        model().attribute("resultDtoSet", new HashSet<>(List.of(resultDto))),
                        model().attribute(Constants.STUDENT, StudentController.loggedInStudent),
                        forwardedUrl("/views/viewResult.jsp")
                );
    }

    @Test
    void  persistPlayedQuizMethodTest() throws Exception {
        UserDto existingStudentDto = new UserDto(Constants.DUMMY_USERNAME_STUDENT, Constants.PASSWORD, Constants.USER_TYPE_STUDENT);
        existingStudentDto.setId(1);
        User existingUser = ModelMapperClass.getMapper().map(existingStudentDto, User.class);
        ResultDto resultDto = new ResultDto();
        StudentController.loggedInStudent = existingUser;
        doNothing().when(studentService).playQuiz(new HashMap<>(), StudentController.loggedInStudent);
        when(studentService.retrieveAttemptedQuizzes(StudentController.loggedInStudent)).thenReturn(new HashSet<>(List.of(resultDto)));
        mockMvc.perform(post("/student/persistPlayedQuiz")
                .param("quizId", String.valueOf(9))
                .param("answerList", "a")
                .param("answerList", "b"))
                .andExpectAll(
                        status().isOk(),
                        view().name("viewResult"),
                        model().attributeExists("resultDtoSet", Constants.STUDENT, Constants.MESSAGE),
                        model().attribute("resultDtoSet", new HashSet<>(List.of(resultDto))),
                        model().attribute(Constants.STUDENT, StudentController.loggedInStudent),
                        forwardedUrl("/views/viewResult.jsp")
                );
    }


}
