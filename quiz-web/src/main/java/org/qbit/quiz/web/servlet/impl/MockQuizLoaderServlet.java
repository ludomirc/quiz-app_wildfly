package org.qbit.quiz.web.servlet.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.qbit.quiz.model.impl.Answer;
import org.qbit.quiz.model.impl.Person;
import org.qbit.quiz.model.impl.Quiz;
import org.qbit.quiz.service.AnswerService;
import org.qbit.quiz.service.QuizPageService;
import org.qbit.quiz.service.QuizService;
import org.qbit.quiz.web.LinkEnum;
import org.qbit.quiz.service.PersonService;
import org.qbit.quiz.web.servlet.BaseHttpServlet;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by beniamin.czaplicki on 2016-05-17.
 */
@WebServlet(name = "mockQuizLoaderServlet", urlPatterns = "/mockquizloader")
public class MockQuizLoaderServlet extends BaseHttpServlet {

    Logger logger = LogManager.getLogger(MockQuizLoaderServlet.class);

    @EJB(beanName = "PersonBean")
    private PersonService personService;

    @EJB(beanName = "QuizBean")
    private QuizService quizService;

    @EJB(beanName = "AnswerBean")
    private AnswerService answerService;

    @EJB(beanName = "QuizPageBean")
    private QuizPageService quizPageService;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);

        resp = openHtmlPage(resp, "Mock quiz load servlet");

        Writer out = resp.getWriter();
        out.append("<h1>Simple quiz mock data loader</h1><br/>");

        loadPerson(out);

        loadQuiz(out);

        out.append(LinkEnum.Index.getHTML(getBaseUrl(req), "return to index"));
        out.flush();

        closeHtmlPage(resp);
    }

    private void loadPerson(Writer out) throws IOException {
        Person person = new Person();
        person.setDateOfBirth("10-11-1960");
        person.setFirstName("Leszek");
        person.setLastName("Skoczylas");
        personService.insert(person);

        out.append("Person loaded<br/>");
    }

    private void loadQuiz(Writer out) throws IOException {

        Person person = personService.findByName(new Person(null, "Skoczylas", null)).get(0);
        //LocalDate localDate = LocalDate.now();
        //Quiz quiz = new Quiz(person,java.sql.Date.valueOf(localDate), null,null);

    /*    List<Answer> answerList = new ArrayList<Answer>();
        for(int i=0; i < 10; i++){
            Answer answer = new Answer();
            answer.setAnswerOrderId(i+1);
            answerService.insert(answer);
        }
        answerList =  answerService.findAll();*/

        //  quiz.setAnswers(answerList);
        quizService.prepareQuizSets(person);
        //quiz.setAnswers(answerList);
        // quizService.update(quiz);
        out.append("quiz loaded<br/>");
    }

}
