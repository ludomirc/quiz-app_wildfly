package org.qbit.quiz.model.impl;

import org.qbit.quiz.model.AbstractPersistentObject;

import javax.persistence.*;

/**
 * Created by beniamin.czaplicki on 2016-05-30.
 */
@Entity
@Table(name = "ANSWER")
@NamedQueries({
        @NamedQuery(
                name = "Answer.findAll",
                query = "SELECT a FROM Answer a"
        ),
        @NamedQuery(
                name = "Answer.findById",
                query = "SELECT a FROM Answer a where a.id = :answerId"
        ),
        @NamedQuery(
                name = "Answer.findByQuizId",
                query = "SELECT a FROM Answer a where a.quiz.id = :quizId order by a.answerOrderId"
        ),
        @NamedQuery(
                name = "Answer.findByQuizIdAnswerOrderId",
                query = "SELECT a FROM Answer a join fetch a.quizPage where a.quiz.id = :quizId and a.answerOrderId= :answerOrderId"
        ),

})
public class Answer extends AbstractPersistentObject {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUIZ_PAGE_ID")
    QuizPage quizPage;

    @Column(name = "RESPONSE")
    String response;

    @Column(name = "ANSWER_ORDER_ID")
    Integer answerOrderId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="QUIZ_ID")
    Quiz quiz;

    public Answer() {
    }

    public Answer(QuizPage quizPage, String response, Integer answerOrderId, Quiz quiz) {
        this.quizPage = quizPage;
        this.response = response;
        this.answerOrderId = answerOrderId;
        this.quiz = quiz;
    }

    public QuizPage getQuizPage() {
        return quizPage;
    }

    public void setQuizPage(QuizPage quizPage) {
        this.quizPage = quizPage;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Integer getAnswerOrderId() {
        return answerOrderId;
    }

    public void setAnswerOrderId(Integer answerOrderId) {
        this.answerOrderId = answerOrderId;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public String toString() {
        return "Answer{" +
                ", response='" + response + '\'' +
                ", answerOrderId=" + answerOrderId +
                '}';
    }
}
