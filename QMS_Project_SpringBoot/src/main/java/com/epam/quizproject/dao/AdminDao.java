package com.epam.quizproject.dao;

import com.epam.quizproject.dto.UserDto;
import com.epam.quizproject.models.User;
import com.epam.quizproject.util.EntityManagerFactoryCreator;
import com.epam.quizproject.util.ModelMapperClass;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class AdminDao {
    private static AdminDao adminDao;
    AdminDao(){

    }

    public static AdminDao getInstance(AdminDao mockedAdminDao){
        adminDao = mockedAdminDao;
        return getInstance();
    }
    public static AdminDao getInstance(){
        if(adminDao == null)
            adminDao = new AdminDao();
        return adminDao;
    }
    private EntityManagerFactory entityManagerFactory = EntityManagerFactoryCreator.getInstance().getEntityManagerFactory();

    public void persistUser(UserDto user) {
        User userToBeAdded = ModelMapperClass.getMapper().map(user, User.class);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(userToBeAdded);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Optional<User> fetchAdmin(UserDto user) {
        User userToBeFetched = ModelMapperClass.getMapper().map(user, User.class);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Optional<User> admin = entityManager.createQuery("SELECT user FROM User user where user.userType =:admin", User.class)
                .setParameter("admin", "admin")
                .getResultList().stream().filter(user1 -> user1.equals(userToBeFetched)).findFirst();
        entityManager.close();
        return admin;
    }

    public Optional<User> fetchUser(UserDto user){
        User userToBeFetched = ModelMapperClass.getMapper().map(user, User.class);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Optional<User> admin = entityManager.createQuery("SELECT user FROM User user", User.class).getResultList().stream().filter(user1 -> user1.equals(userToBeFetched)).findFirst();
        entityManager.close();
        return admin;
    }


    public Set<UserDto> getUsers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Set<User> registeredUsers = new HashSet<>(entityManager.createQuery("SELECT user FROM User user", User.class).getResultList());
        entityManager.close();
        return new HashSet<>(registeredUsers.stream().map(user -> ModelMapperClass.getMapper().map(user, UserDto.class)).collect(Collectors.toSet()));
    }

}
