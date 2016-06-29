package org.qbit.quiz.web.pages.profile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.qbit.quiz.model.impl.Person;
import org.qbit.quiz.model.impl.Quiz;
import org.qbit.quiz.service.PersonService;
import org.qbit.quiz.service.QuizService;
import org.qbit.quiz.web.interceptor.Log;
import org.qbit.quiz.web.pages.person.PersonForm;
import org.qbit.quiz.web.util.StringUtils;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by beniamin.czaplicki on 2016-06-17.
 */
@Named
@RequestScoped
public class ProfileController implements Serializable {

    private Logger logger = LogManager.getLogger(ProfileController.class);

    @EJB(mappedName = "java:global/quiz-web/PersonBean")
    private PersonService personService;

    @EJB(mappedName = "java:global/quiz-web/QuizBean")
    private QuizService quizBean;

    @Inject
    private PersonForm personForm;

    private String firstName;
    private String lastName;

    private List<Quiz> quizList;

    public ProfileController() {
    }

    @Log
    public String update() {
        String updateStatus = "updateFailed";
        logger.debug(personForm.toString());
        Person upPerson = new Person(firstName, lastName, null);
        upPerson.setId(personForm.getId());
        boolean isUpdate = personService.update(upPerson);
        if (isUpdate) {
            personForm.setFirstName(upPerson.getFirstName());
            personForm.setLastName(upPerson.getLastName());
            updateStatus = "updateSuccess";
        }
        return updateStatus;
    }

    private void loadQuizList() {
        if (quizList == null) {
            Person idPerson = new Person();
            idPerson.setId(personForm.getId());
            quizList = quizBean.findByPerson(idPerson);
        }
    }

    private void loadPerson() {
        firstName = personForm.getFirstName();
        lastName = personForm.getLastName();
    }

    //getters ---------------------------------------------------------
    public PersonForm getPersonForm() {
        return personForm;
    }

    public String getLastName() {
        if (StringUtils.isEmpty(lastName)) {
            loadPerson();
        }
        return lastName;
    }

    public String getFirstName() {
        if (StringUtils.isEmpty(firstName)) {
            loadPerson();
        }
        return firstName;
    }

    @Log
    public List<Quiz> getQuizList() {
        if (quizList == null) loadQuizList();
        return quizList;
    }

    //seders ---------------------------------------------------------
    public void setPersonForm(PersonForm personForm) {
        this.personForm = personForm;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setQuizList(List<Quiz> quizList) {
        this.quizList = quizList;
    }
}
