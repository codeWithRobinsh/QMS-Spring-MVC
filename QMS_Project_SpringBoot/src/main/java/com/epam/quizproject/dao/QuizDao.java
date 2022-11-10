package com.epam.quizproject.dao;

import com.epam.quizproject.dto.QuizDto;
import com.epam.quizproject.exceptions.QuizManagementException;
import com.epam.quizproject.models.Question;
import com.epam.quizproject.models.Quiz;
import com.epam.quizproject.util.Constants;
import com.epam.quizproject.util.EntityManagerFactoryCreator;
import com.epam.quizproject.util.ModelMapperClass;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class QuizDao {
    private static QuizDao quizDao;
    private QuizDao(){

    }

    public static QuizDao getInstance(QuizDao quizDao1){
        quizDao = quizDao1;
        return getInstance();
    }
    public static QuizDao getInstance(){
        if(quizDao == null)
            quizDao = new QuizDao();
        return quizDao;
    }

    private EntityManagerFactory emf = EntityManagerFactoryCreator.getInstance().getEntityManagerFactory();

    public int persistCreatedQuiz(QuizDto newQuizDto){
        Quiz quiz = ModelMapperClass.getMapper().map(newQuizDto, Quiz.class);
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(quiz);
        entityManager.getTransaction().commit();
        entityManager.close();
        return quiz.getId();
    }

    public boolean persistAddQuestionToQuiz(int quizId, int questionId) throws QuizManagementException {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            Quiz quiz = entityManager.find(Quiz.class, quizId);
            Question question = entityManager.find(Question.class, questionId);

            quiz.getQuestionSet().add(question);
            quiz.setNoOfQuestions(quiz.getNoOfQuestions() + 1);
            quiz.setMarks(quiz.getMarks() + question.getMarks());

            entityManager.persist(quiz);
            entityManager.persist(question);
            entityManager.getTransaction().commit();

        }catch (Exception e){
            throw new QuizManagementException(Constants.EITHER_QUIZ_OR_QUESTION_DO_NOT_EXISTS);
        }finally {
            entityManager.close();
        }
        return true;
    }

    public QuizDto deleteQuiz(int quizId) throws QuizManagementException {
        EntityManager entityManager = emf.createEntityManager();
        Quiz quizToBeDeleted;
        QuizDto quizDto;
        try {
            entityManager.getTransaction().begin();
            quizToBeDeleted = entityManager.find(Quiz.class, quizId);
            quizDto = ModelMapperClass.getMapper().map(quizToBeDeleted, QuizDto.class);
            entityManager.remove(quizToBeDeleted);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            throw new QuizManagementException(Constants.NO_QUIZ_EXISTS);
        }finally {
            entityManager.close();
        }
        return quizDto;
    }

    public Set<QuizDto> getQuizzes()  {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Set<Quiz> quizzes =  new HashSet<>(entityManager.createQuery(Constants.RETRIEVE_QUIZZES_QUERY, Quiz.class).getResultList());
        entityManager.close();
        return quizzes.stream().map(quiz -> ModelMapperClass.getMapper().map(quiz, QuizDto.class)).collect(Collectors.toSet());
    }

}
