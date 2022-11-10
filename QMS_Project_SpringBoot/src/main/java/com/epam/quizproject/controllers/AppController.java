package com.epam.quizproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {
    @RequestMapping("/homePage")
    public ModelAndView loadHomePage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("homePage");
        return mv;
    }

    @RequestMapping("/exitPage")
    public ModelAndView loadExitPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("exitPage");
        return mv;
    }


}
