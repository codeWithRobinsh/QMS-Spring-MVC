package com.epam.quizproject.mockito.controllers;

import com.epam.quizproject.controllers.QuestionController;
import com.epam.quizproject.controllers.QuizController;
import com.epam.quizproject.dto.OptionsDto;
import com.epam.quizproject.dto.QuestionDto;
import com.epam.quizproject.dto.QuizDto;
import com.epam.quizproject.models.Options;
import com.epam.quizproject.models.Question;
import com.epam.quizproject.services.api.QuestionService;
import com.epam.quizproject.services.api.QuizService;
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

import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = QuestionController.class)
class QuestionControllerTest {
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
    void processQuestionDetailsMethodTest() throws Exception {
        mockMvc.perform(post("/question/createQuestionForm")
                        .contentType("application/json"))
                .andExpectAll(
                        status().isOk(),
                        view().name("createQuestion"),
                        forwardedUrl("/views/createQuestion.jsp")
                );

    }
    @Test
    void retrieveQuestionsByTagFormMethodTest() throws Exception {
        mockMvc.perform(post("/question/retrieveQuestionsByTagForm")
                        .contentType("application/json")
                )
                .andExpectAll(
                        status().isOk(),
                        view().name("retrieveQuestionsByTagForm"),
                        forwardedUrl("/views/retrieveQuestionsByTagForm.jsp")
                );

    }

    @Test
    void createQuestionMethodTest() throws Exception {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(9);
        questionDto.setTag(Constants.DUMMY_TAG);

        Question question = ModelMapperClass.getMapper().map(questionDto, Question.class);

        Options option = new Options();
        option.setOptionA("aa");
        option.setOptionB("bb");
        option.setOptionC("cc");
        option.setOptionD("dd");
        questionDto.setOptions(option);
        option.setQuestion(question);

        OptionsDto optionsDto = ModelMapperClass.getMapper().map(option, OptionsDto.class);

        when(questionService.createQuestion(question)).thenReturn(true);

        mockMvc.perform(post("/question/persistQuestion")
                        .requestAttr("questionDto", questionDto)
                        .requestAttr("optionsDto", optionsDto)
                )
                .andExpectAll(
                        status().isOk(),
                        view().name("success"),
                        model().attributeExists("message"),
                        model().attribute(Constants.MESSAGE, "question has been created successfully"),
                        forwardedUrl("/views/success.jsp")
                );

    }

    @Test
    void retrieveQuestionsByTagMethodTest() throws Exception {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(9);
        questionDto.setTag(Constants.DUMMY_TAG);
        Question question = ModelMapperClass.getMapper().map(questionDto, Question.class);
        Options option = new Options();
        option.setOptionA("aa");
        option.setOptionB("bb");
        option.setOptionC("cc");
        option.setOptionD("dd");
        questionDto.setOptions(option);
        option.setQuestion(question);
        OptionsDto optionsDto = ModelMapperClass.getMapper().map(option, OptionsDto.class);
        String tag = Constants.DUMMY_TAG;
        when(questionService.retrieveQuestionsByTag(tag)).thenReturn(new HashSet<>(List.of(questionDto)));

        mockMvc.perform(post("/question/retrieveQuestionByTag")
                        .param("questionTag", tag))
                .andExpectAll(
                        status().isOk(),
                        view().name("displayQuestions"),
                        model().attributeExists("questionTag", "questions"),
                        model().attribute("questionTag", tag),
                        model().attribute("questions", new HashSet<>(List.of(questionDto))),
                        forwardedUrl("/views/displayQuestions.jsp")
                );

    }
}
