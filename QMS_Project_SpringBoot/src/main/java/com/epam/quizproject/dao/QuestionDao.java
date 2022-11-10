package com.epam.quizproject.dao;

import com.epam.quizproject.dto.QuestionDto;
import com.epam.quizproject.models.Question;
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
public class QuestionDao {
    private static QuestionDao questionDao;
    private QuestionDao(){

    }

    public static QuestionDao getInstance(QuestionDao questiondao){
        questionDao = questiondao;
        return getInstance();
    }
    public static QuestionDao getInstance(){
        if (questionDao == null)
            questionDao = new QuestionDao();
        return questionDao;
    }

    private EntityManagerFactory emf = EntityManagerFactoryCreator.getInstance().getEntityManagerFactory();

    public void persistCreatedQuestion(Question question){
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(question);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Set<QuestionDto> retrieveQuestions(){
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Set<Question> questions  = new HashSet<>(entityManager.createQuery(Constants.RETRIEVE_QUESTIONS_QUERY, Question.class).getResultList());
        entityManager.close();
        return questions.stream().map(question -> ModelMapperClass.getMapper().map(question, QuestionDto.class)).collect(Collectors.toSet());
    }
}
