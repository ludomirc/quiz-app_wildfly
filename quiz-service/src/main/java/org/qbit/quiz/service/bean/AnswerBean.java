package org.qbit.quiz.service.bean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.qbit.quiz.model.impl.Answer;
import org.qbit.quiz.model.impl.Quiz;
import org.qbit.quiz.service.AnswerService;
import org.qbit.quiz.service.util.EJBLoggerInterceptor;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionAttributeType.SUPPORTS;

/**
 * Created by beniamin.czaplicki on 2016-05-31.
 */
@Stateless
@Remote(AnswerService.class)
@Interceptors(EJBLoggerInterceptor.class)
public class AnswerBean implements AnswerService {

    Logger logger = LogManager.getLogger(AnswerBean.class);

    @PersistenceContext(unitName = "jpa-quiz-unit")
    private EntityManager em;

    @Override
    public List<Answer> findAll() {
        return em.createNamedQuery("Answer.findAll").getResultList();
    }

    @Override
    @TransactionAttribute(value = REQUIRED)
    public boolean delete(Answer answer) {
        Answer result = em.find(Answer.class, answer.getId());
        em.remove(result);
        return true;
    }

    @Override
    @TransactionAttribute(value = REQUIRED)
    public boolean update(Answer answer) {
        Answer result = em.find(Answer.class, answer.getId());
        result.setAnswerOrderId(answer.getAnswerOrderId());
        result.setResponse(answer.getResponse());
        result.setVersion(answer.getVersion());
        return true;
    }

    @Override
    @TransactionAttribute(value = REQUIRED)
    public boolean insert(Answer answer) {
        em.persist(answer);
        return true;
    }

    @Override
    public List<Answer> findByName(Answer answer) {
        return null;
    }

    @Override
    public Answer findById(Answer answer) {
        return em.find(Answer.class, answer.getId());
    }

    @Override
    public List<Answer> findByQuiz(Quiz quiz) {
        return em.createNamedQuery("Answer.findByQuizId").setParameter("quizId", quiz.getId()).getResultList();
    }

    @Override
    public long pagesPerQuiz(Quiz quiz) {
        Long result = ((Long) em.createQuery("select count(a) from Answer a where a.quiz.id = :quizId").setParameter("quizId", quiz.getId()).getSingleResult());
        return result == null ? 0 : result;
    }

    @Override
    @TransactionAttribute(value = SUPPORTS)
    public Answer findByAnswerOrderId(Integer answerOrderId, Quiz quiz) {
        Query query = em.createNamedQuery("Answer.findByQuizIdAnswerOrderId");
        query.setParameter("quizId", quiz.getId()).setParameter("answerOrderId", answerOrderId);
        return (Answer) query.getSingleResult();
    }
}
