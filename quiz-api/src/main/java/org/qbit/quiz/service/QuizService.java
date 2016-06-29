package org.qbit.quiz.service;

import org.qbit.quiz.model.impl.Person;
import org.qbit.quiz.model.impl.Quiz;

import java.util.List;

/**
 * Created by beniamin.czaplicki on 2016-05-31.
 */
public interface QuizService extends BaseService<Quiz> {
    public List<Quiz> findByPerson(Person person);

    public boolean prepareQuizSets(Person person);
}
