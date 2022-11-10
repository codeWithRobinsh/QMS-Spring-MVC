package com.epam.quizproject.services;

import com.epam.quizproject.dao.QuizDao;
import com.epam.quizproject.dto.QuizDto;
import com.epam.quizproject.exceptions.QuizManagementException;
import com.epam.quizproject.services.api.QuizService;
import com.epam.quizproject.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {
    @Autowired
    private QuizDao quizDao;
    private static QuizService quizService;
    private QuizServiceImpl(){

    }

    public static QuizService getInstance(){
        if(quizService == null)
            quizService = new QuizServiceImpl();
        return quizService;
    }

    @Override
    public int createQuiz(QuizDto newQuiz) throws QuizManagementException {
        if (QuizDao.getInstance().getQuizzes().contains(newQuiz))
            throw new QuizManagementException(Constants.QUIZ_ALREADY_EXISTS);
        return quizDao.persistCreatedQuiz(newQuiz);

    }

    @Override
    public boolean addQuestionToQuiz(int quizId, int questionId) throws QuizManagementException {
        return quizDao.persistAddQuestionToQuiz(quizId, questionId);
    }

    @Override
    public QuizDto deleteQuiz(int quizId) throws QuizManagementException {
        return quizDao.deleteQuiz(quizId);
    }

    @Override
    public Set<QuizDto> retrieveQuizzes() throws QuizManagementException {
        Set<QuizDto> quizzes = quizDao.getQuizzes();
        if (quizzes.isEmpty())
            throw new QuizManagementException(Constants.NO_QUIZ_EXISTS);
        return quizzes;
    }

    @Override
    public Set<QuizDto> retrieveQuizzesByTag(String tag) throws QuizManagementException{
        Set<QuizDto> quizzes = quizDao.getQuizzes();
        if (!quizzes.isEmpty()){
            quizzes = quizzes.stream().filter(quizDto -> quizDto.getTag().equalsIgnoreCase(tag)).collect(Collectors.toSet());
        }
        if (quizzes.isEmpty())
            throw new QuizManagementException(Constants.NO_QUIZ_EXISTS);
        return quizzes;
    }

    @Override
    public QuizDto retrieveQuizById(int quizId) throws QuizManagementException {
        Set<QuizDto> quizzes = quizDao.getQuizzes();
        Optional<QuizDto> quizDtoOptional = Optional.empty();
        if (!quizzes.isEmpty()){
            quizDtoOptional = quizzes.stream().filter(quizDto -> quizDto.getId() == quizId).findFirst();
        }
        if (quizDtoOptional.isEmpty())
            throw new QuizManagementException(Constants.NO_QUIZ_EXISTS);
        return quizDtoOptional.get();
    }
}
