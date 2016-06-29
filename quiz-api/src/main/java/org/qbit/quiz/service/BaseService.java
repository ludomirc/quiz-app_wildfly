package org.qbit.quiz.service;

import java.util.List;

/**
 * Created by beniamin.czaplicki on 2016-05-24.
 */
public interface BaseService<T> {
    List<T> findAll();
    T findById(T t);
    List<T> findByName(T t);
    boolean insert(T t);
    boolean update(T t);
    boolean delete(T t);
}
