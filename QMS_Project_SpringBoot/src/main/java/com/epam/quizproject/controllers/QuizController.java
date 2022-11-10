package com.epam.quizproject.controllers;

import com.epam.quizproject.dto.QuestionDto;
import com.epam.quizproject.dto.QuizDto;
import com.epam.quizproject.exceptions.QuizManagementException;
import com.epam.quizproject.models.Question;
import com.epam.quizproject.services.api.QuestionService;
import com.epam.quizproject.services.api.QuizService;
import com.epam.quizproject.util.Constants;
import com.epam.quizproject.util.ModelMapperClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;
    @Autowired
    private QuestionService questionService;

    //tested1
    @RequestMapping("/createQuizForm")
    public ModelAndView processQuizDetails() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("createQuiz");
        return mv;
    }
    //tested6
    @PostMapping("/persistQuiz")
    public ModelAndView storeQuiz(@RequestParam(value = "tag", required = false) String tag, @RequestParam(value = "difficultyLevel", required = false) String difficultyLevel, @ModelAttribute("quizDto") QuizDto quizDto) {
        ModelAndView mv  = new ModelAndView();
        try {
            int quizId = quizService.createQuiz(quizDto);
            mv.addObject("quizId", quizId);
            mv.addObject(Constants.MESSAGE, "Quiz created successfully !");
            mv.setViewName(Constants.SUCCESS);

        } catch (QuizManagementException e) {
            mv.addObject(Constants.MESSAGE, e.getMessage());
            mv.setViewName("fail");
        }
        return mv;
    }

    //tested2
    @RequestMapping("/addQuestionToQuizForm")
    public ModelAndView processAddQuestionToQuizDetails() throws QuizManagementException {
        ModelAndView mv = new ModelAndView();
        Set<QuizDto> quizDtoSet = quizService.retrieveQuizzes();
        Set<QuestionDto> questionDtoSet = questionService.retrieveQuestions();
        mv.addObject("availableQuizzes", quizDtoSet);
        mv.addObject("availableQuestions", questionDtoSet);
        mv.setViewName("addQuestionToQuiz");
        return mv;
    }
    //tested7
    @PostMapping("/addQuestionToQuiz")
    public ModelAndView addQuestionToQuiz(@RequestParam(value = "quizId", required = false) int quizId, @RequestParam(value = "questionId", required = false)int questionId) throws QuizManagementException {
        ModelAndView mv = new ModelAndView();
        QuizDto quizDto = quizService.retrieveQuizById(quizId);
        Optional<QuestionDto> question = questionService.retrieveQuestions().stream().filter(questionDto -> questionDto.getId() == questionId).findFirst();
        Question  foundQuestion = ModelMapperClass.getMapper().map(question, Question.class);
        if(question.isPresent() && question.get().getTag().equalsIgnoreCase(quizDto.getTag()) && !quizDto.getQuestionSet().contains(foundQuestion)) {
            try {
                quizService.addQuestionToQuiz(quizId, questionId);
                mv.addObject(Constants.MESSAGE, "question has been added successfully !");
                mv.setViewName(Constants.SUCCESS);

            } catch (QuizManagementException e) {
                mv.addObject(Constants.MESSAGE, e.getMessage());
                mv.setViewName("fail");
            }

        }else{
            mv.addObject(Constants.MESSAGE, "Invalid input");
            mv.setViewName("fail");
        }
        return mv;
    }

    //tested3
    @RequestMapping("/deleteQuizForm")
    public ModelAndView processDeleteQuizDetails() throws QuizManagementException {
        Set<QuizDto> quizDtoSet = quizService.retrieveQuizzes();
        ModelAndView mv = new ModelAndView();
        mv.addObject(Constants.QUIZZES, quizDtoSet);
        mv.setViewName("deleteQuiz");
        return mv;
    }
    //tested8
    @PostMapping("/deleteQuiz")
    public ModelAndView deleteQuiz(@RequestParam(value = "quizId", required = false) int quizId)  {
        ModelAndView mv = new ModelAndView();
        try {
            QuizDto quizDto = quizService.deleteQuiz(quizId);
            mv.addObject("deleted quiz", quizDto);
            mv.addObject(Constants.MESSAGE, "Quiz deleted successfully");
            mv.setViewName(Constants.SUCCESS);
        }catch (QuizManagementException e){
            mv.addObject(Constants.MESSAGE, e.getMessage());
            mv.setViewName("fail");
        }
        return mv;
    }

    //tested4
    @RequestMapping("/retrieveQuizzes")
    public ModelAndView retrieveQuizzes() {
        ModelAndView mv = new ModelAndView();
        try {
            Set<QuizDto> quizDtoSet = quizService.retrieveQuizzes();
            mv.addObject(Constants.QUIZZES, quizDtoSet);
            mv.setViewName("displayQuizzes");
        } catch (QuizManagementException e) {
            mv.addObject(Constants.MESSAGE, e.getMessage());
            mv.setViewName("fail");
        }
        return mv;
    }

    //tested5
    @RequestMapping("/retrieveQuizzesByTag")
    public ModelAndView retrieveQuizzesByTag() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("retrieveQuizzesByTag");
        return mv;
    }
    //testing...9
    @PostMapping("/quizzesByTag")
    public ModelAndView retrieveQuizzesByGivenTag(String quizTag) {
        ModelAndView mv = new ModelAndView();
        try {
            Set<QuizDto> quizDtoSet = quizService.retrieveQuizzesByTag(quizTag);
            mv.addObject(Constants.QUIZZES, quizDtoSet);
            mv.setViewName("displayQuizzes");
        } catch (QuizManagementException e) {
            mv.addObject(Constants.MESSAGE, e.getMessage());
            mv.setViewName("fail");
        }
        return mv;
    }
}
