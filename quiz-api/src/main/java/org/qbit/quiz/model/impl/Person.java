package org.qbit.quiz.model.impl;

import org.qbit.quiz.model.AbstractPersistentObject;

import javax.persistence.*;

/**
 * Created by beniamin.czaplicki on 2016-05-20.
 */
@Entity
@Table(name = "PERSON")
@NamedQueries({
        @NamedQuery(
                name = "Person.findByLastName",
                query = "SELECT p FROM Person p WHERE p.lastName LIKE :lastName"
        ),
        @NamedQuery(
                name = "Person.findByFullName",
                query = "SELECT p FROM Person p WHERE p.firstName = :firstName and p.lastName = :lastName"
        ),
        @NamedQuery(
                name = "Person.findAll",
                query = "SELECT p FROM Person p"
        )
})
public class Person extends AbstractPersistentObject {

    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "DATE_OF_BIRTH")
    private String dateOfBirth;

    public Person() {
    }

    public Person(String firstName, String lastName, String dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                '}';
    }
}
