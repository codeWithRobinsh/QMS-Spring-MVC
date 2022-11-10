package com.epam.quizproject.dto;

import com.epam.quizproject.models.Quiz;
import com.epam.quizproject.models.User;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class ResultDto {
    private int id;

    private User student;

    private Quiz quiz;

    private List<String> submittedAnswer = new ArrayList<>();
    private double marksScored = 0;

    public ResultDto(){

    }
    public ResultDto(User student, Quiz quiz, List<String> submittedAnswer){
        this.student = student;
        this.quiz = quiz;
        this.submittedAnswer = submittedAnswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public List<String> getSubmittedAnswer() {
        return submittedAnswer;
    }

    public void setSubmittedAnswer(List<String> submittedAnswer) {
        this.submittedAnswer = submittedAnswer;
    }

    public double getMarksScored() {
        return marksScored;
    }

    public void setMarksScored(double marksScored) {
        this.marksScored = marksScored;
    }

    @Override
    public String toString() {
        return "ResultDto{" +
                "id=" + id +
                ", student=" + student +
                ", quiz=" + quiz +
                ", submittedAnswer=" + submittedAnswer +
                ", marksScored=" + marksScored +
                '}';
    }
}
