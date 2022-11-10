package com.epam.quizproject.models;

import com.epam.quizproject.util.Constants;
import com.epam.quizproject.util.StringListConverter;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST) ////
    @JoinColumn(name = Constants.COLUMN_NAME_STUDENT_ID, referencedColumnName = Constants.COLUMN_NAME_ID)
    private User student;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = Constants.COLUMN_NAME_QUIZ_ID, referencedColumnName = Constants.COLUMN_NAME_ID)
    private Quiz quiz;

    @Convert(converter = StringListConverter.class)
    private List<String> submittedAnswer = new ArrayList<>();
    private double marksScored = 0;

    public Result(){

    }
    public Result(User student, Quiz quiz, List<String> submittedAnswer){
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
        return "Result{" +
                "id=" + id +
                ", student=" + student +
                ", quiz=" + quiz +
                ", submittedAnswer=" + submittedAnswer +
                ", marksScored=" + marksScored +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Double.compare(result.getMarksScored(), getMarksScored()) == 0 && Objects.equals(getStudent(), result.getStudent()) && Objects.equals(getQuiz(), result.getQuiz()) && Objects.equals(getSubmittedAnswer(), result.getSubmittedAnswer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudent(), getQuiz(), getSubmittedAnswer(), getMarksScored());
    }
}
