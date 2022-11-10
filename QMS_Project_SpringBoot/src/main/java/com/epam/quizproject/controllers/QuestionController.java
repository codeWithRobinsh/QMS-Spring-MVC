package com.epam.quizproject.controllers;

import com.epam.quizproject.dto.OptionsDto;
import com.epam.quizproject.dto.QuestionDto;
import com.epam.quizproject.exceptions.QuizManagementException;
import com.epam.quizproject.models.Options;
import com.epam.quizproject.models.Question;
import com.epam.quizproject.services.api.QuestionService;
import com.epam.quizproject.util.Constants;
import com.epam.quizproject.util.ModelMapperClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    //tested1
    @RequestMapping("/createQuestionForm")
    public ModelAndView processQuestionDetails() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("createQuestion");
        return mv;
    }

    //tested3
    @PostMapping("/persistQuestion")
    public ModelAndView createQuestion(/*@ModelAttribute("questionDto") */QuestionDto questionDto, @ModelAttribute("optionsDto") OptionsDto optionsDto){
        ModelAndView mv = new ModelAndView();
        try {
            Options options = ModelMapperClass.getMapper().map(optionsDto, Options.class);
            questionDto.setOptions(options);
            Question question = ModelMapperClass.getMapper().map(questionDto, Question.class);
            options.setQuestion(question);

            questionService.createQuestion(question);

            mv.addObject(Constants.MESSAGE, "question has been created successfully");
            mv.setViewName(Constants.SUCCESS);

        } catch (QuizManagementException e) {
            mv.addObject(Constants.MESSAGE, e.getMessage());
            mv.setViewName("fail");
        }
        return mv;
    }

    //tested2
    @RequestMapping("/retrieveQuestionsByTagForm")
    public ModelAndView retrieveQuestionsByTagForm() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("retrieveQuestionsByTagForm");
        return mv;
    }

    //testing4
    @PostMapping("/retrieveQuestionByTag")
    public ModelAndView retrieveQuestionsByTag(String questionTag) {
        ModelAndView mv = new ModelAndView();
        try {
            Set<QuestionDto> questionDtos = questionService.retrieveQuestionsByTag(questionTag);
            mv.addObject("questionTag", questionTag);
            mv.addObject("questions", questionDtos);
            mv.setViewName("displayQuestions");
        } catch (QuizManagementException e) {
            mv.addObject(Constants.MESSAGE, e.getMessage());
            mv.setViewName("fail");
        }
        return mv;
    }
}
