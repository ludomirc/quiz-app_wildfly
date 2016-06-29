package org.qbit.quiz.service;

import org.qbit.quiz.model.impl.Person;

import java.util.List;

/**
 * Created by beniamin.czaplicki on 2016-05-24.
 */
public interface PersonService extends BaseService<Person> {

        public List<Person> findByFullName(Person person);
}
