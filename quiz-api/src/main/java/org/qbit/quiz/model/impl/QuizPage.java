package org.qbit.quiz.model.impl;

import org.qbit.quiz.model.AbstractPersistentObject;

import javax.persistence.*;

/**
 * Created by beniamin.czaplicki on 2016-06-07.
 */
@Entity
@Table(name = "QUIZ_PAGE")
@NamedQueries({
        @NamedQuery(
                name = "QuizPage.findAll",
                query = "SELECT qp FROM QuizPage qp order by qp.id"
        )
})
public class QuizPage extends AbstractPersistentObject {

    @Column(name = "QUESTION", length = 1000)
    String question;
    @Column(name = "A", length = 1000)
    String a;
    @Column(name = "B", length = 1000)
    String b;
    @Column(name = "C", length = 1000)
    String c;
    @Column(name= "D", length = 1000)
    String d;
    @Column(name = "ANSWER")
    String answer;

    public QuizPage() {
    }

    public QuizPage(String question, String a, String b, String c, String d, String answer) {
        this.question = question;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "QuizPage{" +
                "question='" + question + '\'' +
                ", a='" + a + '\'' +
                ", b='" + b + '\'' +
                ", c='" + c + '\'' +
                ", d='" + d + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
