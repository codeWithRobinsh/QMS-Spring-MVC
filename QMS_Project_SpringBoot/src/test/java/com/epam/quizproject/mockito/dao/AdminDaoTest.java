package com.epam.quizproject.mockito.dao;

import com.epam.quizproject.dao.AdminDao;
import com.epam.quizproject.dto.UserDto;
import com.epam.quizproject.models.User;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminDaoTest {
    @InjectMocks
    AdminDao adminDao;

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

    List<User> userList;

    User user;

    @BeforeEach
    void setUp(){
        user = new User(Constants.USERNAME_ADMIN, Constants.PASSWORD, Constants.USERNAME_ADMIN);
        userList = new ArrayList<>();
        userList.add(user);
        userTypedQuery.setParameter("admin", "admin");
    }

    @Test
    void persistUserMethodTest() {
        UserDto userDto =  ModelMapperClass.getMapper().map(user, UserDto.class);
        when(emf.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        doNothing().when(entityManager).persist(user);
        ArgumentCaptor<User> acUser = ArgumentCaptor.forClass(User.class);
        adminDao.persistUser(userDto);
        verify(entityManager).persist(acUser.capture());
        assertEquals(user, acUser.getValue());

    }

    @Test
    void fetchAdminMethodTest() {
        UserDto userDto = ModelMapperClass.getMapper().map(user, UserDto.class);
        when(emf.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.createQuery("SELECT user FROM User user where user.userType =:admin", User.class)).thenReturn(userTypedQuery);
        when(userTypedQuery.setParameter("admin", "admin")).thenReturn(userTypedQuery);
        when(userTypedQuery.getResultList()).thenReturn(userList);
        assertEquals(Optional.of(user), adminDao.fetchAdmin(userDto));
        assertEquals(Optional.empty(), adminDao.fetchAdmin(new UserDto()));

    }

    @Test
    void fetchUserMethodTest() {
        UserDto userDto = ModelMapperClass.getMapper().map(user, UserDto.class);
        when(emf.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.createQuery("SELECT user FROM User user", User.class)).thenReturn(userTypedQuery);
        when(userTypedQuery.getResultList()).thenReturn(userList);
        assertEquals(Optional.of(user), adminDao.fetchUser(userDto));
        assertEquals(Optional.empty(), adminDao.fetchUser(new UserDto()));
    }

    @Test
    void getUsersMethodTest() {
        when(emf.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(entityTransaction);
        when(entityManager.createQuery("SELECT user FROM User user", User.class)).thenReturn(userTypedQuery);
        when(userTypedQuery.getResultList()).thenReturn(userList);
        Set<UserDto> userDtoSet = userList.stream().map(user1 -> ModelMapperClass.getMapper().map(user1, UserDto.class)).collect(Collectors.toSet());
        assertEquals(userDtoSet, adminDao.getUsers());
    }
}
