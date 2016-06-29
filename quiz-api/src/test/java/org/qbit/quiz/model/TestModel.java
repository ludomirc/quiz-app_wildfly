package org.qbit.quiz.model;

/**
 * Created by beniamin.czaplicki on 2016-05-20.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.qbit.quiz.model.impl.Answer;
import org.qbit.quiz.model.impl.Person;
import org.qbit.quiz.model.impl.Quiz;
import org.qbit.quiz.model.impl.QuizPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestModel {

    Logger logger = LogManager.getLogger(TestModel.class);
    private EntityManagerFactory entityManagerFactory;

    @BeforeMethod
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("test-quiz-unit");
    }

    @Test()
    public void testPerson() {
        logger.debug(" testPerson() begin");
        EntityManager entityManager = beginSession();

        Person person = getPerson();

        String id = person.getId();
        entityManager.persist(person);
        entityManager.getTransaction().commit();
        Person persistPerson = entityManager.find(Person.class, id);

        logger.debug(persistPerson.toString());

        entityManager.close();
        logger.debug("testPerson() end");

    }



   /* @Test()
    public void testAnswer() {

        logger.debug("testAnswer() begin");
        EntityManager entityManager = beginSession();

        getPersistAnswers(entityManager);
        entityManager.getTransaction().commit();

        logger.debug("answer list: ");
        List<Answer> answerList = entityManager.createNamedQuery("Answer.findAll").getResultList();
        if (!answerList.isEmpty()) {
            final int[] id = {0};
            answerList.forEach(answer -> {
                logger.debug(++id[0] + " " + answer.toString());
            });
        }

        entityManager.close();
        logger.debug("testAnswer() end");
    }*/

    @Test
    public void testQuiz() {
        logger.debug("testQuiz() begin");
        EntityManager em = beginSession();

        loadQuizPageToDB(em);

        Quiz quiz = new Quiz();
        Person person = getPerson();
        em.persist(person);

        quiz.setPerson(person);

        List<QuizPage> quizPageList = em.createQuery("select qp from QuizPage qp").setMaxResults(10).getResultList();


        List<Answer> answers = new ArrayList<>();
        int order = 1;
        for (QuizPage elQizPage : quizPageList) {
            Answer answer = new Answer();
            answer.setQuizPage(elQizPage);
            answer.setAnswerOrderId(order++);
            answers.add(answer);
        }

        logger.debug("answer size before save: " + answers.size());
        quiz.setAnswers(answers);

        em.persist(quiz);
        em.getTransaction().commit();

        //--- open new session ---
        List<Quiz> quizList = em.createNamedQuery("Quiz.findAll").getResultList();
        Quiz resultQuiz = quizList.get(0);
        logger.debug(resultQuiz);
        List<Answer> answerList = resultQuiz.getAnswers();

        logger.debug("answer list size from quiz: " + answerList.size());
        Answer resultAnswer =  answers.get(0);

        logger.debug("resultAnswer.getQuizPage() " +  resultAnswer.getQuizPage());

        em.close();
        logger.debug("testQuiz() end");
    }

    private EntityManager beginSession() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        return em;
    }

    @AfterMethod
    public void destroy() {
        entityManagerFactory.close();
    }

    private Person getPerson() {
        Person person = new Person();
        person.setDateOfBirth("10-11-1960");
        person.setFirstName("Leszek");
        person.setLastName("Skoczylas");
        return person;
    }

    private void loadQuizPage(EntityManager em) {

    }

    private void loadQuizPageToDB(EntityManager em) {

        URL sourceFileURL = TestModel.class.getResource("/ITIL.xlsx");

        final int Question = 0;
        final int A = 1;
        final int B = 2;
        final int C = 3;
        final int D = 4;
        final int AN = 5;

        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(sourceFileURL.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;

        Iterator rows = sheet.rowIterator();
        QuizPage quizPage = null;
        while (rows.hasNext()) {
            row = (XSSFRow) rows.next();

            quizPage = new QuizPage();
            quizPage.setQuestion(row.getCell(Question).getStringCellValue());

            quizPage.setA(row.getCell(A).getStringCellValue());
            quizPage.setB(row.getCell(B).getStringCellValue());
            quizPage.setC(row.getCell(C).getStringCellValue());
            quizPage.setD(row.getCell(D).getStringCellValue());
            quizPage.setAnswer(row.getCell(AN).getStringCellValue());

            em.persist(quizPage);
        }
    }
}
