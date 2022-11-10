package com.epam.quizproject.dto;

import com.epam.quizproject.models.Question;
import com.epam.quizproject.models.Result;

import java.util.Formatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class QuizDto {
    private int id;
    private String tag;
    private String difficultyLevel;
    private int noOfQuestions = 0;
    private double marks = 0;

    private Set<Result> result = new HashSet<>();

    private Set<Question> questionSet = new HashSet<>();

    public QuizDto(){

    }

    public QuizDto(String tag, String difficultyLevel, int noOfQuestions, double marks, Set<Question> questionSet) {
        this.tag = tag;
        this.difficultyLevel = difficultyLevel;
        this.noOfQuestions = noOfQuestions;
        this.marks = marks;
        this.questionSet = questionSet;
    }

    public QuizDto(String tag, String difficultyLevel) {
        this.tag = tag;
        this.difficultyLevel = difficultyLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public int getNoOfQuestions() {
        return noOfQuestions;
    }

    public void setNoOfQuestions(int noOfQuestions) {
        this.noOfQuestions = noOfQuestions;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public Set<Question> getQuestionSet() {
        return questionSet;
    }

    public void setQuestionSet(Set<Question> questionSet) {
        this.questionSet = questionSet;
    }

    public Set<Result> getResult() {
        return result;
    }

    public void setResult(Set<Result> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        try (Formatter formatter = new Formatter()) {
            formatter.format("%7s %15s %15s %10s %10s ", id, tag, difficultyLevel, noOfQuestions, marks);
            return formatter.toString();
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizDto quiz = (QuizDto) o;
        return getNoOfQuestions() == quiz.getNoOfQuestions() && Double.compare(quiz.getMarks(), getMarks()) == 0 && getTag().equals(quiz.getTag()) && getDifficultyLevel().equals(quiz.getDifficultyLevel()) && getQuestionSet().equals(quiz.getQuestionSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTag(), getDifficultyLevel(), getNoOfQuestions(), getMarks(), getQuestionSet());
    }
}
