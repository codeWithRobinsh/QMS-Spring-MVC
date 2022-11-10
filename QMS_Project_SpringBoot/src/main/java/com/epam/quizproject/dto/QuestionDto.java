package com.epam.quizproject.dto;

import com.epam.quizproject.models.Options;

import java.util.Objects;

public class QuestionDto {

    private int id;
    private String tag;
    private String difficultyLevel;
    private double marks;
    private String content;

    private Options options;
    private String answer;

    public QuestionDto(){

    }

    public QuestionDto(String tag, String difficultyLevel, double marks, String content, String answer, Options options) {
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
        return "QuestionDto{" +
                "tag='" + tag + '\'' +
                ", difficultyLevel='" + difficultyLevel + '\'' +
                ", marks=" + marks +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionDto that = (QuestionDto) o;
        return Double.compare(that.getMarks(), getMarks()) == 0 && getTag().equals(that.getTag()) && getDifficultyLevel().equals(that.getDifficultyLevel()) && getContent().equals(that.getContent()) && getAnswer().equals(that.getAnswer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTag(), getDifficultyLevel(), getMarks(), getContent(), getAnswer());
    }
}
