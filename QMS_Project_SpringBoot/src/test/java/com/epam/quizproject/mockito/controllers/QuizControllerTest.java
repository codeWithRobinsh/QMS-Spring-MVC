package com.epam.quizproject.mockito.controllers;

import com.epam.quizproject.controllers.QuizController;
import com.epam.quizproject.dto.QuestionDto;
import com.epam.quizproject.dto.QuizDto;
import com.epam.quizproject.services.api.QuestionService;
import com.epam.quizproject.services.api.QuizService;
import com.epam.quizproject.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = QuizController.class)
class QuizControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    @Autowired
    private QuizService quizService;
    @MockBean
    @Autowired
    private QuestionService questionService;

    @Test
    void processQuizDetailsMethodTest() throws Exception {
        mockMvc.perform(post("/quiz/createQuizForm")
                        .contentType("application/json"))
                .andExpectAll(
                        status().isOk(),
                        forwardedUrl("/views/createQuiz.jsp")
                );

    }

    @Test
    void processAddQuestionToQuizDetailsMethodTest() throws Exception {
        mockMvc.perform(post("/quiz/addQuestionToQuizForm")
                        .contentType("application/json"))
                .andExpectAll(
                        status().isOk(),
                        forwardedUrl("/views/addQuestionToQuiz.jsp")
                );

    }

    @Test
    void processDeleteQuizDetailsMethodTest() throws Exception {
        mockMvc.perform(post("/quiz/deleteQuizForm")
                        .contentType("application/json"))
                .andExpectAll(
                        status().isOk(),
                        forwardedUrl("/views/deleteQuiz.jsp")
                );

    }

    @Test
    void retrieveQuizzesMethodTest() throws Exception {
        mockMvc.perform(post("/quiz/retrieveQuizzes")
                        .contentType("application/json"))
                .andExpectAll(
                        status().isOk(),
                        view().name("displayQuizzes"),
                        forwardedUrl("/views/displayQuizzes.jsp")
                );

    }

    @Test
    void retrieveQuizzesByTagMethodTest() throws Exception {
        mockMvc.perform(post("/quiz/retrieveQuizzesByTag")
                        .contentType("application/json"))
                .andExpectAll(
                        status().isOk(),
                        view().name("retrieveQuizzesByTag"),
                        forwardedUrl("/views/retrieveQuizzesByTag.jsp")
                );

    }
    @Test
    void storeQuizMethodTest() throws Exception {
        QuizDto newQuizDto = new QuizDto(Constants.DUMMY_TAG, Constants.MEDIUM);
        when(quizService.createQuiz(newQuizDto)).thenReturn(newQuizDto.getId());
        mockMvc.perform(post("/quiz/persistQuiz")
                .sessionAttr("quizDto", newQuizDto)
                .param("tag", Constants.USERNAME_ADMIN)
                .param("difficultyLevel", Constants.PASSWORD))
                .andExpectAll(
                        status().isOk(),
                        view().name("success"),
                        model().attributeExists("quizId", "message"),
                        model().attribute("quizId", newQuizDto.getId()),
                        model().attribute(Constants.MESSAGE, "Quiz created successfully !"),
                        forwardedUrl("/views/success.jsp")
                );

    }

    @Test
    void addQuestionToQuizMethodTest() throws Exception {
        int quizId = 9;
        int questionId = 9;
        QuizDto newQuizDto = new QuizDto(Constants.DUMMY_TAG, Constants.MEDIUM);
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(9);
        questionDto.setTag(Constants.DUMMY_TAG);
        when(quizService.retrieveQuizById(9)).thenReturn(newQuizDto);
        when(questionService.retrieveQuestions()).thenReturn(new HashSet<>(List.of(questionDto)));
        when(quizService.addQuestionToQuiz(quizId, questionId)).thenReturn(true);
        mockMvc.perform(post("/quiz/addQuestionToQuiz")
                        .param("quizId", String.valueOf(9))
                        .param("questionId", String.valueOf(9))
                        )
                .andExpectAll(
                        status().isOk(),
                        view().name("success"),
                        model().attributeExists("message"),
                        model().attribute(Constants.MESSAGE, "question has been added successfully !"),
                        forwardedUrl("/views/success.jsp")
                );

    }

    @Test
    void deleteQuizMethodTest() throws Exception {
        int quizId = 9;
        QuizDto newQuizDto = new QuizDto(Constants.DUMMY_TAG, Constants.MEDIUM);
        when(quizService.deleteQuiz(9)).thenReturn(newQuizDto);
        mockMvc.perform(post("/quiz/deleteQuiz")
                        .param("quizId", String.valueOf(9)))
                .andExpectAll(
                        status().isOk(),
                        view().name("success"),
                        model().attributeExists("deleted quiz", "message"),
                        model().attribute("deleted quiz", newQuizDto),
                        model().attribute(Constants.MESSAGE, "Quiz deleted successfully"),
                        forwardedUrl("/views/success.jsp")
                );

    }

    @Test
    void retrieveQuizzesByGivenTagMethodTest() throws Exception {
        String tag = Constants.DUMMY_TAG;
        QuizDto newQuizDto = new QuizDto(Constants.DUMMY_TAG, Constants.MEDIUM);
        when(quizService.retrieveQuizzesByTag(tag)).thenReturn(new HashSet<>(List.of(newQuizDto)));
        mockMvc.perform(post("/quiz/quizzesByTag")
                        .param("quizTag", tag))
                .andExpectAll(
                        status().isOk(),
                        view().name("displayQuizzes"),
                        model().attributeExists(Constants.QUIZZES),
                        model().attribute(Constants.QUIZZES, new HashSet<>(List.of(newQuizDto))),
                        forwardedUrl("/views/displayQuizzes.jsp")
                );

    }



}
