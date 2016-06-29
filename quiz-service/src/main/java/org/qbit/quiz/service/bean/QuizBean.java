package org.qbit.quiz.service.bean;

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
import org.qbit.quiz.service.util.EJBLoggerInterceptor;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

import static javax.ejb.TransactionAttributeType.REQUIRED;

/**
 * Created by beniamin.czaplicki on 2016-05-31.
 */
@Stateless
@Remote(QuizService.class)
@Interceptors(EJBLoggerInterceptor.class)
public class QuizBean implements QuizService {

    Logger logger = LogManager.getLogger(QuizBean.class);
    @PersistenceContext(unitName = "jpa-quiz-unit")
    private EntityManager em;

    @EJB(beanName = "PersonBean")
    PersonService personService;

    @EJB(beanName = "AnswerBean")
    AnswerService answerService;

    @EJB(beanName = "QuizPageBean")
    QuizPageService quizPageService;

    public QuizBean() {
    }

    @Override
    public List<Quiz> findAll() {
        return em.createNamedQuery("Quiz.findAll").getResultList();
    }

    @Override
    public boolean delete(Quiz quiz) {
        Quiz result = em.find(Quiz.class, quiz.getId());
        em.remove(result);
        return true;
    }

    @Override
    @TransactionAttribute(value = REQUIRED)
    public boolean update(Quiz quiz) {
        Quiz result = em.find(Quiz.class, quiz.getId());
        result.setVersion(quiz.getVersion());
        result.setFinishDate(quiz.getFinishDate());
        result.setPerson(quiz.getPerson());
        result.setAnswers(quiz.getAnswers());
        result.setStartDate(quiz.getStartDate());
        return true;
    }

    @Override
    @TransactionAttribute(value = REQUIRED)
    public boolean insert(Quiz quiz) {
        int answerCount = 10;
    /*    List<QuizPage> selectedPageList = getRandomQuizPages(answerCount);
        quiz.setAnswers(getAnswersPage(quiz, answerCount, selectedPageList));*/
        logger.debug("method not implemented jet");
        return false;
    }

    private List<Answer> getAnswersPage(Quiz quiz, List<QuizPage> selectedPageList) {
        List<Answer> answers = new ArrayList<>();
        logger.debug("size of selectedQuizPage: " + selectedPageList.size());
        try {
            for (int i = 0; i < selectedPageList.size(); i++) {
                Answer answer = new Answer();
                answer.setAnswerOrderId(i + 1);
                answer.setQuiz(quiz);
                answer.setQuizPage(selectedPageList.get(i));
                em.persist(answer);
                answers.add(answer);
            }
        } catch (IndexOutOfBoundsException ex) {
            logger.error("===========" + ex);
            throw ex;
        }
        return answers;
    }

    /**
     * Generate random quiz sets and store them in db
     *
     * @param person
     * @return if success return true else return false
     */
    public boolean prepareQuizSets(Person person) {
        boolean status = true;

        /*
         if person not found return false
         */
        Person rPerson = personService.findById(person);
        if (rPerson == null) {
            status = false;
            return status;
        }

        /*
        prepare random quiz sets
        insert new quiz to db
        */
        ArrayList<QuizPage> quizPages = (ArrayList<QuizPage>) quizPageService.findAll();
        int quizPagePerSet = 40;
        List<QuizPage> tempPages = null;
        Quiz tempQuiz = null;
        int qCounter = 0;
        while (!quizPages.isEmpty()) {
            tempPages = getRandomQuizPages(quizPagePerSet, quizPages);
            tempQuiz = new Quiz();
            tempQuiz.setPerson(person);
            tempQuiz.setAnswers(getAnswersPage(tempQuiz, tempPages));
            em.persist(tempQuiz);
            logger.debug("add new quiz id: " + ++qCounter);
        }
        return status;

    }

    private List<QuizPage> getRandomQuizPages(int answerCount, ArrayList<QuizPage> quizPages) {

        logger.debug("size of quizPages:" + quizPages.size() + " quiz page per quiz: " + answerCount);

        Set<QuizPage> selectedQuizPage = new HashSet<>();
        Random random = new Random();
        QuizPage tempPage = null;
        while ((selectedQuizPage.size() < answerCount) && !quizPages.isEmpty()) {

            int r = random.nextInt(quizPages.size());
            tempPage = quizPages.get(r);
            boolean isAdded = selectedQuizPage.add(tempPage);

            if (isAdded) {
                quizPages.remove(tempPage);
                logger.debug("performed remove from quizPages");
            }
            logger.debug("random number r: " + r + " size of selectedQuizPage set: " + selectedQuizPage.size());
        }
        return new ArrayList<>(selectedQuizPage);
    }

    @Override
    public List<Quiz> findByName(Quiz quiz) {
        return null;
    }

    @Override
    public Quiz findById(Quiz quiz) {
        return em.find(Quiz.class, quiz.getId());
    }

    @Override
    public List<Quiz> findByPerson(Person person) {
        Person resultPerson = personService.findById(person);
        if (resultPerson == null) {
            logger.debug("end  findByPerson: ");
            return null;
        }

        List<Quiz> quiz = em.createNamedQuery("Quiz.findByPerson").setParameter("person", resultPerson).getResultList();
        return quiz;
    }
}
