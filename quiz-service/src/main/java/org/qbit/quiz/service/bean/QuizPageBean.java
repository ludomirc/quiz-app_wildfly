package org.qbit.quiz.service.bean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.qbit.quiz.model.impl.QuizPage;
import org.qbit.quiz.service.QuizPageService;
import org.qbit.quiz.service.util.EJBLoggerInterceptor;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionAttributeType.SUPPORTS;

/**
 * Created by beniamin.czaplicki on 2016-06-08.
 */
@Stateless
@Remote(QuizPageService.class)
@Interceptors(EJBLoggerInterceptor.class)
public class QuizPageBean implements QuizPageService {

    Logger logger = LogManager.getLogger(QuizBean.class);
    @PersistenceContext(unitName = "jpa-quiz-unit")
    private EntityManager em;

    @Override
    @TransactionAttribute(value = SUPPORTS)
    public List<QuizPage> findAll() {
        return em.createNamedQuery("QuizPage.findAll").getResultList();
    }

    @Override
    @TransactionAttribute(value = REQUIRED)
    public boolean delete(QuizPage quizPage) {
        QuizPage result = em.find(QuizPage.class, quizPage.getId());
        em.remove(result);
        return true;
    }

    @Override
    @TransactionAttribute(value = REQUIRED)
    public boolean update(QuizPage quizPage) {
        QuizPage result = em.find(QuizPage.class, quizPage.getId());
        result.setQuestion(quizPage.getQuestion());
        result.setAnswer(quizPage.getAnswer());
        result.setA(quizPage.getA());
        result.setB(quizPage.getA());
        result.setC(quizPage.getB());
        result.setD(quizPage.getC());
        result.setAnswer(quizPage.getAnswer());
        return true;
    }

    @Override
    @TransactionAttribute(value = REQUIRED)
    public boolean insert(QuizPage quizPage) {
        em.persist(quizPage);
        return true;
    }

    @Override
    public List<QuizPage> findByName(QuizPage quizPage) {
        return null;
    }

    @Override
    public QuizPage findById(QuizPage quizPage) {
        return em.find(QuizPage.class, quizPage.getId());
    }
}
