package org.qbit.quiz.service;

import org.qbit.quiz.model.impl.Answer;
import org.qbit.quiz.model.impl.Quiz;

import java.util.List;

/**
 * Created by beniamin.czaplicki on 2016-05-31.
 */
public interface AnswerService extends BaseService<Answer> {
    List<Answer> findByQuiz(Quiz quiz);
    Answer findByAnswerOrderId(Integer answerOrderId,Quiz quiz);
    long pagesPerQuiz(Quiz quiz);
}
