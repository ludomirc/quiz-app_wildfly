package org.qbit.quiz.web.pages.person;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.qbit.quiz.model.impl.Person;
import org.qbit.quiz.service.PersonService;
import org.qbit.quiz.web.interceptor.Log;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by beniamin.czaplicki on 2016-05-24.
 */

@Named
@RequestScoped
public class PersonController implements Serializable {

    Logger logger = LogManager.getLogger(PersonController.class);

    @EJB(mappedName = "java:global/quiz-web/PersonBean")
    private PersonService personService;

    @Inject
    PersonForm personForm;

    private String firstName = null;
    private String lastName = null;
    private String id = null;

    public PersonController() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PersonForm getPersonForm() {
        return personForm;
    }

    public void setPersonForm(PersonForm personForm) {
        this.personForm = personForm;
    }

    @Log
    public String find() {
        String outcome = "back";

        List<Person> resultList = personService.findByName(new Person(getFirstName(), getLastName(), null));
        logger.debug("first name: " +getFirstName() + " last name: " + getLastName() + " result list size: " + resultList.size());
        if (!resultList.isEmpty()) {
            Person retPerson = resultList.get(0);

            personForm.setId(retPerson.getId());
            personForm.setFirstName(retPerson.getFirstName());
            personForm.setLastName(retPerson.getLastName());
            outcome = "success";

        }
        logger.debug("outcome value : " + outcome);

        return outcome;
    }

    @Log
    public String createNewUser() {
        Person person = new Person(getFirstName(), getLastName(), null);
        boolean isCreate = personService.insert(person);
        if (isCreate) {
            personForm.setId(person.getId());
            personForm.setFirstName(person.getFirstName());
            personForm.setLastName(person.getLastName());
            return "createNew";
        }
        return "back";
    }
}
