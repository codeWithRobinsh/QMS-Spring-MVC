package com.epam.quizproject.mockito.controllers;

import com.epam.quizproject.controllers.AdminController;
import com.epam.quizproject.dto.UserDto;
import com.epam.quizproject.models.User;
import com.epam.quizproject.services.api.AdminService;
import com.epam.quizproject.services.api.Login;
import com.epam.quizproject.services.api.Register;
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

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AdminController.class)
class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    @Autowired
    public AdminService adminService;
    @MockBean
    @Autowired
    public Register register;
    @MockBean
    @Autowired
    public Login login;

    @Test
    void loadAdminDashboardMethodTest() throws Exception {
        mockMvc.perform(post("/admin/adminDashboard")
                .contentType("application/json"))
                .andExpectAll(
                    status().isOk(),
                    forwardedUrl("/views/adminPage.jsp")
                );
    }

    @Test
    void loadRegistrationMenuMethodTest() throws Exception {
        mockMvc.perform(post("/admin/registrationForm")
                .contentType("application/json"))
                .andExpectAll(
                        status().isOk(),
                        view().name("adminRegistrationPage"),
                        forwardedUrl("/views/adminRegistrationPage.jsp")
                );
    }

    @Test
    void storeAdminMethodTest() throws Exception {
        UserDto newAdminDto = new UserDto(Constants.ADMIN_3, Constants.PASSWORD, Constants.USER_TYPE_ADMIN);
        when(adminService.registerAdmin()).thenReturn(register);
        mockMvc.perform(post("/admin/registerAdmin")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(newAdminDto)))
                        .andExpectAll(
                                status().isOk(),
                                view().name("success"),
                                forwardedUrl("/views/success.jsp")
                        );

    }

    @Test
    void loadLoginMenuMethodTest() throws Exception {
        mockMvc.perform(post("/admin/loginForm").contentType("application/json"))
                .andExpectAll(
                        status().isOk(),
                        view().name("adminLoginPage"),
                        forwardedUrl("/views/adminLoginPage.jsp")
                );
    }

    @Test
    void signInAdminMethodTest() throws Exception {
        UserDto existingAdminDto = new UserDto(Constants.USERNAME_ADMIN, Constants.PASSWORD, Constants.USER_TYPE_ADMIN);
        existingAdminDto.setId(1);
        User existingUser = ModelMapperClass.getMapper().map(existingAdminDto, User.class);
        when(adminService.loginAdmin()).thenReturn(login);
        when(adminService.loginAdmin().login(existingAdminDto)).thenReturn(Optional.of(existingUser));
        mockMvc.perform(post("/admin/loginAdmin")
                        .sessionAttr("userDto", existingAdminDto)
                        .param("username", Constants.USERNAME_ADMIN)
                        .param("password", Constants.PASSWORD)
                        .param("userType", Constants.USER_TYPE_ADMIN))
                .andExpectAll(
                        status().isOk(),
                        view().name("adminOperationsMenu"),
                        model().attributeExists("userDto", "message"),
                        model().attribute("userDto", existingAdminDto),
                        model().attribute(Constants.MESSAGE, "login successful"),
                        forwardedUrl("/views/adminOperationsMenu.jsp")
                );
    }



}
