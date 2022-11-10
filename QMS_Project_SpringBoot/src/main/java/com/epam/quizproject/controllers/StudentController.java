package com.epam.quizproject.controllers;

import com.epam.quizproject.dto.QuizDto;
import com.epam.quizproject.dto.ResultDto;
import com.epam.quizproject.dto.UserDto;
import com.epam.quizproject.exceptions.QuizManagementException;
import com.epam.quizproject.models.User;
import com.epam.quizproject.services.api.QuizService;
import com.epam.quizproject.services.api.StudentService;
import com.epam.quizproject.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private QuizService quizService;

    public static User loggedInStudent;

    //tested1
    @RequestMapping("/studentDashboard")
    public ModelAndView loadStudentDashboard() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("studentPage");
        return mv;
    }

    //tested2
    @RequestMapping("/registrationForm")
    public ModelAndView loadRegistrationMenu() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("studentRegistrationPage");
        return mv;
    }

    //tested5
    @PostMapping("/registerStudent")
    public ModelAndView signUpStudent(@ModelAttribute("userDto") UserDto userDto){
        ModelAndView mv  = new ModelAndView();
        try {
            studentService.registerStudent().register(userDto);
            mv.addObject(Constants.MESSAGE, "registration successful");
            mv.setViewName("success");
        }catch (QuizManagementException e){
            mv.addObject(Constants.MESSAGE, e.getMessage());
            mv.setViewName("fail");
        }
        return mv;
    }

    //tested3
    @RequestMapping("/loginForm")
    public ModelAndView loadLoginMenu() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("studentLoginPage");
        return mv;
    }

    //tested6
    @PostMapping("/loginStudent")
    public ModelAndView signInStudent(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password, @RequestParam(value = "userType", required = false) String userType, @ModelAttribute("userDto") UserDto userDto)  {
        ModelAndView mv  = new ModelAndView();

        Optional<User> user = studentService.loginStudent().login(userDto);
        if (user.isPresent()) {
            loggedInStudent = user.get();
            mv.setViewName("studentOperationsMenu");
            mv.addObject(Constants.MESSAGE, "login successful");
            mv.addObject(Constants.STUDENT, user.get());

        }else {
            mv.addObject(Constants.MESSAGE, "login failed, try Again");
            mv.setViewName("studentLoginPage");
        }
        return mv;
    }

    //tested4
    @RequestMapping("/playQuizForm")
    public ModelAndView playQuizForm() {
        ModelAndView mv = new ModelAndView();
        try {
            mv.addObject("quizzes", quizService.retrieveQuizzes());
            mv.setViewName("playQuizForm");
        } catch (QuizManagementException e) {
            mv.addObject(Constants.MESSAGE, e.getMessage());
            mv.setViewName("fail");
        }
        return mv;
    }

    //tested7
    @PostMapping("/playQuizMenu")
    public ModelAndView playQuizMenu(int quizId) {
        ModelAndView mv = new ModelAndView();
        try {
            QuizDto quizDto = quizService.retrieveQuizById(quizId);
            mv.addObject("questions", quizDto.getQuestionSet());
            mv.addObject("quizId", quizId);
            mv.setViewName("playQuiz");
        } catch (QuizManagementException e) {
            mv.addObject(Constants.MESSAGE, e.getMessage());
            mv.setViewName("fail");
        }
        return mv;
    }
    @PostMapping("/persistPlayedQuiz")
    public ModelAndView persistPlayedQuiz(int quizId, String[] answerList){
        ModelAndView mv = new ModelAndView();
        Map<Integer, List<String>> resultMap = new HashMap<>();
        List<String> submissionList = Arrays.asList(answerList);
        resultMap.put(quizId, submissionList);
        try {
            studentService.playQuiz(resultMap, loggedInStudent);
            Set<ResultDto> resultDtoSet = studentService.retrieveAttemptedQuizzes(loggedInStudent);
            mv.addObject(Constants.MESSAGE, "quiz has been completed successfully");
            mv.addObject(Constants.STUDENT, loggedInStudent);
            mv.addObject("resultDtoSet", resultDtoSet);
            mv.setViewName("viewResult");
        }catch (Exception e){
            mv.addObject(Constants.MESSAGE, e.toString());
            mv.setViewName("fail");
        }
        return mv;
    }

    //testing8
    @RequestMapping("/getResult")
    public ModelAndView getResult() {
        ModelAndView mv = new ModelAndView();
        try {
            Set<ResultDto> resultDtoSet = studentService.retrieveAttemptedQuizzes(loggedInStudent);
            mv.addObject(Constants.STUDENT, loggedInStudent);
            mv.addObject("resultDtoSet", resultDtoSet);
            mv.setViewName("viewResult");
        } catch (QuizManagementException e) {
            mv.addObject(Constants.MESSAGE, e.getMessage());
            mv.setViewName("fail");
        }
        return mv;
    }

}
