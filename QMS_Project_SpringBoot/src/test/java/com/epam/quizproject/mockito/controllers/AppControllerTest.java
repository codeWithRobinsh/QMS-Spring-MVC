package com.epam.quizproject.mockito.controllers;

import com.epam.quizproject.controllers.AppController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AppController.class)
class AppControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void loadHomePageMethodTest() throws Exception {
        mockMvc.perform(post("/homePage").contentType("application/json"))
                .andExpectAll(
                        status().isOk(),
                        forwardedUrl("/views/homePage.jsp")
                );
    }

    @Test
    void loadExitPageMethodTest() throws Exception {
        mockMvc.perform(post("/exitPage").contentType("application/json"))
                .andExpectAll(
                        status().isOk(),
                        forwardedUrl("/views/exitPage.jsp")
                );
    }


}
