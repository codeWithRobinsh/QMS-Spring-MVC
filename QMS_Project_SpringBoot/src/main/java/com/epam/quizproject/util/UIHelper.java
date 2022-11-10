package com.epam.quizproject.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UIHelper {
    public static final Logger LOGGER = LogManager.getLogger(UIHelper.class);
    private UIHelper(){

    }
    public static void showAdminUseCases() {
        LOGGER.info(Constants.CREATE_QUIZ_OPTION);
        LOGGER.info(Constants.ADD_QUESTION_TO_QUIZ_OPTION);
        LOGGER.info(Constants.DELETE_QUIZ_OPTION);
        LOGGER.info(Constants.CREATE_QUESTION_OPTIONS);
        LOGGER.info(Constants.RETRIEVE_QUIZZES_OPTION);
        LOGGER.info(Constants.RETRIEVE_QUIZZES_BY_OPTION);
        LOGGER.info(Constants.RETRIEVE_QUESTIONS_BY_TAG_OPTION);
        LOGGER.info(Constants.RETURN_TO_ADMIN_DASHBOARD_OPTION);
        LOGGER.info(Constants.EXIT_OPTION);
        LOGGER.info(Constants.RETURN_TO_STUDENT_DASHBOARD_OPTION);
    }

    public static void showStudentUseCases() {
        LOGGER.info(Constants.ENTER_ONE_TO_RETRIEVE_QUIZZES);
        LOGGER.info(Constants.ENTER_TWO_TO_RETRIEVE_QUIZZES_BY_TAG);
        LOGGER.info(Constants.ENTER_THREE_TO_PLAY_QUIZ);
        LOGGER.info(Constants.ENTER_FOUR_TO_GET_RESULT);
        LOGGER.info(Constants.ENTER_FIVE_TO_RETURN_TO_STUDENT_DASHBOARD);
        LOGGER.info(Constants.ENTER_SIX_TO_LOGOUT);
        LOGGER.info(Constants.ENTER_SEVEN_TO_RETURN_TO_ADMIN_DASHBOARD);
    }
}
