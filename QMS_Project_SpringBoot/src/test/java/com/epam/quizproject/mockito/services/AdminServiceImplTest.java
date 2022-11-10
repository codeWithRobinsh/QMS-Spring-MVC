package com.epam.quizproject.mockito.services;


import com.epam.quizproject.dao.AdminDao;
import com.epam.quizproject.dto.QuizDto;
import com.epam.quizproject.dto.UserDto;
import com.epam.quizproject.exceptions.QuizManagementException;
import com.epam.quizproject.models.*;
import com.epam.quizproject.services.AdminServiceImpl;
import com.epam.quizproject.services.Authentication;
import com.epam.quizproject.services.api.Login;
import com.epam.quizproject.services.api.Register;
import com.epam.quizproject.util.Constants;
import com.epam.quizproject.util.ModelMapperClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {
    @InjectMocks
    private AdminServiceImpl adminServiceImpl;
    @Mock
    private AdminDao adminDao;
    @Mock
    private Authentication authentication;

    UserDto adminDto;
    UserDto existingAdminDto;

    @BeforeEach
    void setUp(){
        adminDto = new UserDto(Constants.USERNAME_ADMIN, Constants.PASSWORD, Constants.USER_TYPE_ADMIN);
        existingAdminDto = new UserDto(Constants.USERNAME_ADMIN, Constants.PASSWORD, Constants.USER_TYPE_ADMIN);
        AdminDao.getInstance(adminDao);
    }

    @Test
    void registerAdminMethodTest() throws QuizManagementException {
        UserDto newAdmin = new UserDto(Constants.ADMIN_3, Constants.PASSWORD, Constants.USER_TYPE_ADMIN);
        doNothing().when(adminDao).persistUser(newAdmin);
        AdminDao.getInstance(adminDao);
        assertEquals(newAdmin, adminServiceImpl.registerAdmin().register(newAdmin));

        when(adminDao.getUsers()).thenReturn(new HashSet<>(Collections.singletonList(existingAdminDto)));
        assertThrows(QuizManagementException.class, () -> adminServiceImpl.registerAdmin().register(existingAdminDto));
    }

    @Test
    void loginAdminMethodTest() {
        AdminServiceImpl.getInstance();
        UserDto newAdmin = new UserDto(Constants.ADMIN_3, Constants.PASSWORD, Constants.USER_TYPE_ADMIN);
        Optional<User> optionalUser = Optional.of(ModelMapperClass.getMapper().map(existingAdminDto, User.class));
        when(adminDao.fetchAdmin(existingAdminDto)).thenReturn(optionalUser);
        when(authentication.authenticate(ModelMapperClass.getMapper().map(existingAdminDto, User.class))).thenReturn(true);
        assertEquals(optionalUser, adminServiceImpl.loginAdmin().login(existingAdminDto));
        assertEquals(Optional.empty(), adminServiceImpl.loginAdmin().login(new UserDto()));
        when(adminDao.fetchAdmin(newAdmin)).thenReturn(Optional.empty());
        when(authentication.authenticate(ModelMapperClass.getMapper().map(newAdmin, User.class))).thenReturn(true);
        assertEquals(Optional.empty(), adminServiceImpl.loginAdmin().login(newAdmin));
    }


}
