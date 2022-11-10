package com.epam.quizproject.models;

import com.epam.quizproject.util.Constants;
import javax.persistence.*;

import java.util.Objects;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String tag;
    private String difficultyLevel;
    private double marks;
    private String content;

    @OneToOne(mappedBy = Constants.MAPPED_BY_QUESTION, cascade = CascadeType.ALL)
    private Options options;
    private String answer;

    public Question(){

    }

    public Question(String tag, String difficultyLevel, double marks, String content, String answer, Options options) {
        this.tag = tag;
        this.difficultyLevel = difficultyLevel;
        this.marks = marks;
        this.content = content;
        this.answer = answer;
        this.options = options;
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

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "content='" + content + '\'' +
                ", options=" + options +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return getTag().equals(question.getTag()) && getDifficultyLevel().equals(question.getDifficultyLevel()) && getContent().equals(question.getContent()) && getAnswer().equals(question.getAnswer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTag(), getDifficultyLevel(), getContent(), getAnswer());
    }
}
