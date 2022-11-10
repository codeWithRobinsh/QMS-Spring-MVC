package com.epam.quizproject.mockito.services;

import com.epam.quizproject.dao.AdminDao;
import com.epam.quizproject.dto.UserDto;
import com.epam.quizproject.models.User;
import com.epam.quizproject.services.Authentication;
import com.epam.quizproject.util.Constants;
import com.epam.quizproject.util.ModelMapperClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @InjectMocks
    Authentication authentication;
    @Mock
    AdminDao adminDao;

    Set<UserDto> userDtoSet;
    UserDto existingUserDto;

    @BeforeEach
    void setUp() {
        existingUserDto = new UserDto(Constants.USERNAME_ADMIN, Constants.PASSWORD, Constants.USER_TYPE_ADMIN);
        userDtoSet = new HashSet<>();
        userDtoSet.add(existingUserDto);
        AdminDao.getInstance(adminDao);
    }

    @Test
    void authenticateMethodTest() {
        User user = ModelMapperClass.getMapper().map(existingUserDto, User.class);
        when(adminDao.fetchUser(existingUserDto)).thenReturn(Optional.of(user));
        assertTrue(authentication.authenticate(user));
        assertFalse(authentication.authenticate(new User()));

    }
}
