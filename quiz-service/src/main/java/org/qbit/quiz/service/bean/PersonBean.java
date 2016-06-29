package org.qbit.quiz.service.bean;

import org.qbit.quiz.model.impl.Person;
import org.qbit.quiz.service.PersonService;
import org.qbit.quiz.service.util.EJBLoggerInterceptor;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import javax.ejb.TransactionAttribute;

import static javax.ejb.TransactionAttributeType.REQUIRED;

/**
 * Created by beniamin.czaplicki on 2016-05-24.
 */
@Stateless
@Remote(PersonService.class)
@Interceptors(EJBLoggerInterceptor.class)
public class PersonBean implements PersonService {


    @PersistenceContext(unitName = "jpa-quiz-unit")
    private EntityManager em;

    @Override
    public List<Person> findAll() {
        List<Person> personList = (List<Person>) em.createNamedQuery("Person.findAll").getResultList();
        return personList;
    }

    @Override
    @TransactionAttribute(value = REQUIRED)
    public Person findById(Person person) {
        Person result = em.find(Person.class, person.getId());
        return result;
    }

    @Override
    public List<Person> findByName(Person person) {
        List<Person> personList = (List<Person>) em.createNamedQuery("Person.findByLastName").setParameter("lastName", person.getLastName()).getResultList();
        return personList;
    }

    @Override
    public List<Person> findByFullName(Person person) {
        List<Person> personList = (List<Person>) em.createNamedQuery("Person.findByFullName").setParameter("firstName",person.getFirstName()).setParameter("lastName", person.getLastName()).getResultList();
        return personList;
    }

    @Override
    @TransactionAttribute(value = REQUIRED)
    public boolean insert(Person person) {
        em.persist(person);
        return true;
    }

    @Override
    @TransactionAttribute(value = REQUIRED)
    public boolean update(Person person) {
        Person fromDb = findById(person);
        if (person.getDateOfBirth() != null) {
            fromDb.setDateOfBirth(person.getDateOfBirth());
        }
        if (person.getFirstName() != null) {
            fromDb.setFirstName(person.getFirstName());
        }
        if (person.getLastName() != null) {
            fromDb.setLastName(person.getLastName());
        }
        return true;
    }

    @Override
    @TransactionAttribute(value = REQUIRED)
    public boolean delete(Person person) {
        person = findById(person);
        em.remove(person);
        return true;
    }
}
