package com.epam.quizproject.services;

import com.epam.quizproject.dao.AdminDao;
import com.epam.quizproject.dto.UserDto;
import com.epam.quizproject.models.User;
import com.epam.quizproject.util.ModelMapperClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Authentication {
    @Autowired
    private AdminDao adminDao;

    public Authentication(){

    }
    public Authentication(AdminDao adminDao){
        this.adminDao = adminDao;
    }

    public boolean authenticate(User user) {
        UserDto userDto = ModelMapperClass.getMapper().map(user, UserDto.class);
        return adminDao.fetchUser(userDto).isPresent() && adminDao.fetchUser(userDto).get().getPassword().equals(user.getPassword());
    }
}
