package com.epam.quizproject.dao;

import com.epam.quizproject.dto.ResultDto;
import com.epam.quizproject.dto.UserDto;
import com.epam.quizproject.models.Question;
import com.epam.quizproject.models.Quiz;
import com.epam.quizproject.models.Result;
import com.epam.quizproject.models.User;
import com.epam.quizproject.util.EntityManagerFactoryCreator;
import com.epam.quizproject.util.ModelMapperClass;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;


import java.util.*;
import java.util.stream.Collectors;

@Repository
public class StudentDao {
    private static StudentDao studentDao;
    private StudentDao(){

    }

    public static StudentDao getInstance(StudentDao studentDao1){
        studentDao = studentDao1;
        return getInstance();
    }
    public static StudentDao getInstance(){
        if (studentDao == null)
            studentDao = new StudentDao();
        return studentDao;
    }

    private EntityManagerFactory emf = EntityManagerFactoryCreator.getInstance().getEntityManagerFactory();

    public void persistUser(UserDto user) {
        User userToBeAdded = ModelMapperClass.getMapper().map(user, User.class);
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(userToBeAdded);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Optional<User> fetchStudent(UserDto user) {
        User studentToBeFetched = ModelMapperClass.getMapper().map(user, User.class);
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Optional<User> student = entityManager.createQuery("SELECT user FROM User user", User.class).getResultList().stream().filter(user1 -> user1.equals(studentToBeFetched)).findFirst();
        entityManager.close();
        return student;
    }

    public void persistPlayedQuiz(Map<Integer, List<String>> map, User student) {
        int quizId = map.keySet().stream().findFirst().orElse(-1);
        List<String> submittedAnswers = map.get(quizId);
        if(quizId != -1) {
            EntityManager entityManager = emf.createEntityManager();
            entityManager.getTransaction().begin();
            Quiz playedQuiz = entityManager.find(Quiz.class, quizId);
            User studentWhoPlayedQuiz = entityManager.find(User.class, student.getId());

            double score = 0;
            List<Question> questions = playedQuiz.getQuestionSet().stream().toList();
            for (int i = 0; i < playedQuiz.getNoOfQuestions(); i++){
                if(questions.get(i).getAnswer().equalsIgnoreCase(submittedAnswers.get(i))){
                    score += questions.get(i).getMarks();
                }
            }
            Result result = new Result(studentWhoPlayedQuiz, playedQuiz, submittedAnswers);
            result.setMarksScored(score);
            entityManager.persist(result);
            entityManager.getTransaction().commit();
            entityManager.close();
        }

    }

    public  Set<ResultDto> getResult(User student)  {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, student.getId());
        int studentId = user.getId();
        Query resultQuery = entityManager.createNativeQuery("SELECT * FROM Result r JOIN User s ON r.student_id = s.id where s.id=:studentId", Result.class)
                .setParameter("studentId", studentId);
        List<Result> results = resultQuery.getResultList();
        return results.stream().map(result -> ModelMapperClass.getMapper().map(result, ResultDto.class)).collect(Collectors.toSet());
        /*TypedQuery<Result> resultTypedQuery = entityManager.createQuery("SELECT results FROM User s JOIN Result r where s.id=:studentId", Result.class)
                .setParameter("studentId", studentId);
        return new HashSet<>(resultTypedQuery1.getResultList().stream().map(result -> ModelMapperClass.getMapper().map(result, ResultDto.class)).collect(Collectors.toSet()));*/
    }

}
