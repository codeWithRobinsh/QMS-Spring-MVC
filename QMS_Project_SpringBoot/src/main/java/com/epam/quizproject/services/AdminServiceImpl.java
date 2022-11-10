package com.epam.quizproject.services;

import com.epam.quizproject.dao.AdminDao;
import com.epam.quizproject.exceptions.QuizManagementException;
import com.epam.quizproject.models.User;
import com.epam.quizproject.services.api.AdminService;
import com.epam.quizproject.services.api.Login;
import com.epam.quizproject.services.api.Register;
import com.epam.quizproject.util.Constants;
import com.epam.quizproject.util.ModelMapperClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private Authentication authentication;

    public static final Logger LOGGER = LogManager.getLogger(AdminServiceImpl.class);
    private static AdminServiceImpl adminService;
    private AdminServiceImpl(){

    }
    public static AdminServiceImpl getInstance(){
        if(adminService == null)
            adminService = new AdminServiceImpl();
        return adminService;
    }
    public Register registerAdmin() {
        return user -> {
            if(user.getUserType().equalsIgnoreCase(Constants.USER_TYPE_ADMIN) && !adminDao.getUsers().contains(user))
                adminDao.persistUser(user);
            else
                throw new QuizManagementException(Constants.EITHER_USER_DOES_NOT_EXISTS_OR_INCORRECT_USER_TYPE);
            return  user;
        };
    }

    public Login loginAdmin() {
        return user -> {
            Optional<User> admin = Optional.empty();
            if(authentication.authenticate(ModelMapperClass.getMapper().map(user, User.class))) {
                admin = adminDao.fetchAdmin(user);
            }
            return admin;
        };
    }

}
