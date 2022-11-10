package com.epam.quizproject.models;

import com.epam.quizproject.util.Constants;
import javax.persistence.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String tag;
    private String difficultyLevel;
    private int noOfQuestions = 0;
    private double marks = 0;

    @OneToMany(mappedBy = Constants.MAPPED_BY_QUIZ, cascade = CascadeType.ALL)
    private Set<Result> result = new LinkedHashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = Constants.JOIN_TABLE_QUESTION_QUIZ,
            joinColumns = @JoinColumn(name = Constants.COLUMN_NAME_QUIZ_ID, referencedColumnName = Constants.COLUMN_NAME_ID),
            inverseJoinColumns = @JoinColumn(name = Constants.JOIN_COLUMN_NAME_QUESTION_ID, referencedColumnName = Constants.COLUMN_NAME_ID)
    )
    private Set<Question> questionSet = new HashSet<>();

    public Quiz(){

    }

    public Quiz(String tag, String difficultyLevel, int noOfQuestions, double marks, Set<Question> questionSet) {
        this.tag = tag;
        this.difficultyLevel = difficultyLevel;
        this.noOfQuestions = noOfQuestions;
        this.marks = marks;
        this.questionSet = questionSet;
    }

    public Quiz(String tag, String difficultyLevel) {
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
        return "Quiz{" +
                "tag='" + tag + '\'' +
                ", difficultyLevel='" + difficultyLevel + '\'' +
                ", noOfQuestions='" + noOfQuestions + '\'' +
                ", marks=" + marks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return getNoOfQuestions() == quiz.getNoOfQuestions() && Double.compare(quiz.getMarks(), getMarks()) == 0 && getTag().equals(quiz.getTag()) && getDifficultyLevel().equals(quiz.getDifficultyLevel()) && getQuestionSet().equals(quiz.getQuestionSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTag(), getDifficultyLevel(), getNoOfQuestions(), getMarks(), getQuestionSet());
    }
}
