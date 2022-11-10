package com.epam.quizproject.controllers;

import com.epam.quizproject.dto.UserDto;
import com.epam.quizproject.exceptions.QuizManagementException;
import com.epam.quizproject.models.User;
import com.epam.quizproject.services.api.AdminService;
import com.epam.quizproject.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/adminDashboard")
    public ModelAndView loadAdminDashboard() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("adminPage");
        return mv;
    }
    @RequestMapping("/registrationForm")
    public ModelAndView loadRegistrationMenu() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("adminRegistrationPage");
        return mv;
    }
    @PostMapping("/registerAdmin")
    public ModelAndView storeAdmin(UserDto userDto) throws QuizManagementException {
        ModelAndView mv  = new ModelAndView();
        try {
            adminService.registerAdmin().register(userDto);
            mv.addObject(Constants.MESSAGE, "registration successful");
            mv.setViewName(Constants.SUCCESS);
        }catch (QuizManagementException e){
            mv.addObject(Constants.MESSAGE, e.getMessage());
            mv.setViewName("fail");
        }
        return mv;
    }


    @RequestMapping("/loginForm")
    public ModelAndView loadLoginMenu() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("adminLoginPage");
        return mv;
    }

    @PostMapping("/loginAdmin")
    public ModelAndView signInAdmin(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password, @RequestParam(value = "userType", required = false) String userType, @ModelAttribute("userDto") UserDto userDto)  {
        ModelAndView mv  = new ModelAndView();
        Optional<User> user = adminService.loginAdmin().login(userDto);
        if (user.isPresent()) {
            mv.setViewName("adminOperationsMenu");
            mv.addObject(Constants.MESSAGE, "login successful");
            mv.addObject("admin", user.get());

        }else {
            mv.addObject(Constants.MESSAGE, "login failed, try Again");
            mv.setViewName("adminLoginPage");
        }

        return mv;
    }

}
