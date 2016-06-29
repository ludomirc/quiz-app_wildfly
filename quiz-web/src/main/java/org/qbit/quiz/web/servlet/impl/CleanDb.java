package org.qbit.quiz.web.servlet.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.qbit.quiz.model.impl.Answer;
import org.qbit.quiz.model.impl.Person;
import org.qbit.quiz.model.impl.Quiz;
import org.qbit.quiz.model.impl.QuizPage;
import org.qbit.quiz.service.AnswerService;
import org.qbit.quiz.service.PersonService;
import org.qbit.quiz.service.QuizPageService;
import org.qbit.quiz.service.QuizService;
import org.qbit.quiz.web.LinkEnum;
import org.qbit.quiz.web.interceptor.Log;
import org.qbit.quiz.web.servlet.BaseHttpServlet;

import javax.ejb.EJB;
import javax.interceptor.Interceptors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Created by beniamin.czaplicki on 2016-06-10.
 */
@WebServlet(name = "cleanDb", urlPatterns = "/cleandb")
@Log
public class CleanDb extends BaseHttpServlet {

    Logger logger = LogManager.getLogger(CleanDb.class);

    public static final String PARM_MESSAGE = "MESSAGE";
    public static final String VAL_MESSAGE_CLEAN_FALSE = "CLEAN_FALSE";
    public static final String VAL_MESSAGE_CLEAN_DONE = "CLEAN_DONE";

    public static final String PARM_CLEAN = "CLEAN";
    public static final String VAL_CLEAN_YES = "YES";
    public static final String VAL_CLEAN_NO = "NO";

    @EJB(beanName = "AnswerBean")
    AnswerService answerService;

    @EJB(beanName = "PersonBean")
    PersonService personService;

    @EJB(beanName = "QuizBean")
    QuizService quizService;

    @EJB(beanName = "QuizPageBean")
    QuizPageService quizPageService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        resp = openHtmlPage(resp, "Clean db servlet");
        Writer out = resp.getWriter();
        out.append(LinkEnum.CleanDB.getWithParameterHTML(getBaseUrl(req),"?"+PARM_CLEAN+"="+VAL_CLEAN_YES, "perform clean"));
        out.append("<h3>CleanDB servlet</h3><br/>");

        String valClean = req.getParameter(PARM_CLEAN);
        if (valClean != null && valClean.equalsIgnoreCase(VAL_CLEAN_YES)) {
            logger.debug("perform clean");
            String message = clearDb();
            out.append("<b>" + message + "</b><br/>");
            logger.debug("clean stratus: " + message);
        }

        out.append(LinkEnum.Index.getHTML(getBaseUrl(req), "return to index"));
        out.flush();
        closeHtmlPage(resp);
    }

    private String clearDb() {
        String message = VAL_MESSAGE_CLEAN_FALSE;
        try {

            //--clean answer table --
            List<Answer> answerList = answerService.findAll();
            answerList.forEach(elAnswer -> {
                answerService.delete(elAnswer);
            });

            //-- clear quiz table--
            List<Quiz> quizList = quizService.findAll();
            quizList.forEach(elQuiz -> {
                quizService.delete(elQuiz);
            });


            //-- clear person table--
            List<Person> personList = personService.findAll();
            personList.forEach(elPerson -> {
                personService.delete(elPerson);
            });
            //-- clear quiz_page table --
            List<QuizPage> quizPageList = quizPageService.findAll();
            quizPageList.forEach(elQuizPage -> {
                quizPageService.delete(elQuizPage);
            });
            message = VAL_MESSAGE_CLEAN_DONE;
        } catch (Exception ex) {
            logger.error(ex);

        }
        return message;
    }
}
