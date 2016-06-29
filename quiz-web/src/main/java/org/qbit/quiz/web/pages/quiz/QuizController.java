package org.qbit.quiz.web.pages.quiz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.qbit.quiz.model.impl.Answer;
import org.qbit.quiz.model.impl.Person;
import org.qbit.quiz.model.impl.Quiz;
import org.qbit.quiz.model.impl.QuizPage;
import org.qbit.quiz.service.AnswerService;
import org.qbit.quiz.service.PersonService;
import org.qbit.quiz.service.QuizService;
import org.qbit.quiz.web.interceptor.Log;
import org.qbit.quiz.web.pages.person.PersonForm;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;

/**
 * Created by beniamin.czaplicki on 2016-06-02.
 */
@Named
@SessionScoped
public class QuizController implements Serializable {

    private Logger logger = LogManager.getLogger(QuizController.class);

    @EJB(mappedName = "java:global/quiz-web/QuizBean")
    private QuizService quizService;

    @EJB(mappedName = "java:global/quiz-web/PersonBean")
    private PersonService personService;

    @EJB(mappedName = "java:global/quiz-web/AnswerBean")
    private AnswerService answerService;

    @Inject
    private PersonForm personForm;

    @Inject
    private AnswerPanel answerPanel;

    private Quiz quiz;

    private String quizId;

    private QuestionPanel questionPanel;

    /**
     * pagination stuff
     */
    private int totalPages;
    private Integer[] pages;
    private int currentPage;


    public QuizController() {
    }

    public String doQuiz() {
        quiz = null;
        loadData(1);
        return "quiz";
    }

    // Paging actions -----------------------------------------------------------------------------

    public void pageFirst() {
        page(1);
    }

    public void pageNext() {
        page(++currentPage);
    }

    public void pagePrevious() {
        page(--currentPage);
    }

    public void pageLast() {
        page(pages.length);
    }

    private void page(int currentPage) {
        // Load requested page.
        loadData(currentPage);
    }

    private Person loadPerson() {
        logger.debug("loadPerson() begin: ");

        Person person = new Person();
        String personId = personForm.getId();
        logger.debug("personId: " + personId);
        person.setId(personId);
        person = personService.findById(person);
        logger.debug("person value: " + person != null ? person.toString() : "null");
        logger.debug("loadPerson() end: ");
        return person;
    }

    private void loadQuiz() {
        logger.debug("loadQuiz() begin");

        Person person = loadPerson();
        logger.debug("person value: " + person != null ? person.toString() : "null");

        logger.debug("quizId: " + quizId);

        Quiz byId = new Quiz();
        byId.setId(quizId);
        quiz = quizService.findById(byId);

        logger.debug("quiz value: " + quiz != null ? quiz.toString() : "null");
        logger.debug("loadQuiz() end");

    }

    /**
     * method load selected quiz page
     *
     * @param currentPage - minimal value of page 1
     */
    private void loadData(int currentPage) {

        // Load list and totalCount.
        this.currentPage = currentPage;
        if (quiz == null) loadQuiz();

        Answer answerModel = answerService.findByAnswerOrderId(currentPage, quiz);
       int pagesPerQuiz = (int) answerService.pagesPerQuiz(quiz);

        String uAnswer = answerModel.getResponse();
        boolean isUAnswer = uAnswer != null && uAnswer.length() > 0;

        //init user answer
        answerPanel.setData(new String[0]);
        if (isUAnswer) {
            String[] answers = new String[uAnswer.length()];
            for (int i = 0; i < uAnswer.length(); i++) {
                answers[i] = String.valueOf(uAnswer.charAt(i));
            }
            answerPanel.setData(answers);
        }

        logger.debug(answerModel);
        logger.debug(answerModel.getQuizPage());
        QuizPage quizPage = answerModel.getQuizPage();
        questionPanel = new QuestionPanel();
        questionPanel.setQuestion(quizPage.getQuestion());
        questionPanel.setA(quizPage.getA());
        questionPanel.setB(quizPage.getB());
        questionPanel.setC(quizPage.getC());
        questionPanel.setD(quizPage.getD());

        boolean emptyAnswer = answerModel.getResponse() != null && answerModel.getResponse().isEmpty();
        //if()

        // Set currentPage, totalPages and pages.
        pages = new Integer[pagesPerQuiz];
        totalPages = pages.length;
        // Create pages (page numbers for page links).
        for (int i = 0; i < pages.length; i++) {
            pages[i] = i + 1;
        }

        logger.debug("currentPage: " + currentPage + " totalPages: " + totalPages + " totalRows: " + " pages[]: " + pages.length);
    }

    // ActionEvent ------------------------------------------------------------------------------------
    public void page(ActionEvent event) {
        page(((Integer) ((UICommand) event.getComponent()).getValue()));
    }

    // Getters ------------------------------------------------------------------------------------
    public Integer[] getPages() {
        return pages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public QuestionPanel getQuestionPanel() {
        if (questionPanel == null) {
            loadData(currentPage);
        }
        return questionPanel;
    }

    @Log
    public void answer() {

        String[] uAnswers = answerPanel.getData();
        boolean isUAnswer = uAnswers != null && uAnswers.length > 0;
        if (isUAnswer) {
            String result = "";
            for (String elAnswer : uAnswers) {
                result += elAnswer;
            }
            Answer answerModel = answerService.findByAnswerOrderId(currentPage, quiz);
            answerModel.setResponse(result);
            answerService.update(answerModel);
            logger.debug("result: " + result);
        }

    }

    @Log
    public AnswerPanel getAnswerPanel() {
        return answerPanel;
    }

    public String getQuizId() {
        return quizId;
    }

    // Setters ------------------------------------------------------------------------------------

    @Log
    public void setAnswerPanel(AnswerPanel answerPanel) {
        logger.debug(answerPanel.toString());
        this.answerPanel = answerPanel;
    }

    public void setQuestionPanel(QuestionPanel questionPanel) {
        this.questionPanel = questionPanel;
    }


    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }
}
