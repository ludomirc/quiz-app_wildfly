package org.qbit.quiz.web.pages.person;

import org.qbit.quiz.web.interceptor.Log;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by beniamin.czaplicki on 2016-05-24.
 */

@Named
@SessionScoped
public class PersonForm implements Serializable {

    private String firstName;
    private String lastName;
    private String dataOfBarth;
    private String id;

    public PersonForm() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Log
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDataOfBarth() {
        return dataOfBarth;
    }

    public void setDataOfBarth(String dataOfBarth) {
        this.dataOfBarth = dataOfBarth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "PersonForm{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dataOfBarth='" + dataOfBarth + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
