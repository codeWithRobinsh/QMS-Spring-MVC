package com.epam.quizproject.services;


import com.epam.quizproject.dao.StudentDao;
import com.epam.quizproject.dto.ResultDto;
import com.epam.quizproject.exceptions.QuizManagementException;
import com.epam.quizproject.models.User;
import com.epam.quizproject.services.api.Login;
import com.epam.quizproject.services.api.Register;
import com.epam.quizproject.services.api.StudentService;
import com.epam.quizproject.util.Constants;
import com.epam.quizproject.util.ModelMapperClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private Authentication authentication;

    public static final Logger LOGGER = LogManager.getLogger(StudentServiceImpl.class);
    private static StudentService studentService;
    private StudentServiceImpl(){

    }
    public static StudentService getInstance(){
        if (studentService == null)
            studentService = new StudentServiceImpl();
        return studentService;
    }

    @Override
    public Register registerStudent() {
        return user -> {
            if(user.getUserType().equalsIgnoreCase(Constants.USER_TYPE_STUDENT))
                studentDao.persistUser(user);
            return user;
        };
    }

    @Override
    public Login loginStudent() {
        return user -> {
            User userToBeAuthenticated = ModelMapperClass.getMapper().map(user, User.class);
            Optional<User> student = Optional.empty();
            if(authentication.authenticate(userToBeAuthenticated)) {
                student = studentDao.fetchStudent(user);
            }
            return student;
        };
    }

    @Override
    public void playQuiz(Map<Integer, List<String>> map, User student) {
        studentDao.persistPlayedQuiz(map, student);
    }

    @Override
    public Set<ResultDto> retrieveAttemptedQuizzes(User student) throws QuizManagementException {
        Set<ResultDto>  resultSet = studentDao.getResult(student);
        if (resultSet.isEmpty())
            throw new QuizManagementException(Constants.NO_QUIZ_ATTEMPTED);
        return  resultSet;
    }

}
