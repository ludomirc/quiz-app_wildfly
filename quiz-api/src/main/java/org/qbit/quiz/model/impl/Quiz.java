package org.qbit.quiz.model.impl;

import org.qbit.quiz.model.AbstractPersistentObject;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by beniamin.czaplicki on 2016-05-30.
 */
@Entity
@Table(name = "QUIZ")
@NamedQueries({
        @NamedQuery(
                name = "Quiz.findAll",
                query = "SELECT qz FROM Quiz qz"
        ),
        @NamedQuery(
                name = "Quiz.findByPerson",
                query = "select qz FROM Quiz qz where qz.person = :person order by qz.id"
        )
})
public class Quiz extends AbstractPersistentObject {

    public Quiz() {
    }

    public Quiz(Person person, Date startDate, Date finishDate, List<Answer> answers) {
        this.person = person;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.answers = answers;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "FINISH_DATE")
    private Date finishDate;


    @OneToMany(mappedBy="quiz")
    private List<Answer> answers;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                '}';
    }
}
