package org.qbit.quiz.web.pages.quiz;

import org.qbit.quiz.web.interceptor.Log;

import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by beniamin.czaplicki on 2016-06-11.
 */
@Log
@Named
public class QuestionPanel implements Serializable {

    String question;
    String a;
    String b;
    String c;
    String d;


    public QuestionPanel() {
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
}
