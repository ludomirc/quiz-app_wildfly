package org.qbit.quiz.web;

/**
 * Created by beniamin.czaplicki on 2016-05-16.
 */
public enum LinkEnum {

    Index("/_index.jsp"),
    LoadQuizPage("/loadquizpage"),
    MockQuizLoader("/mockquizloader"),
    CleanDB("/cleandb");

    public String value;

    LinkEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getHTML(String baseUrl, String description) {
        String link = "";
        link += "<a href=\"" + baseUrl + getValue() + "\">";
        link += description;
        link += "</a>";
        return link;
    }

    public String getWithParameterHTML(String baseUrl,String params, String description) {
        String link = "";
        link += "<a href=\"" + baseUrl + getValue() + params + "\">";
        link += description;
        link += "</a>";
        return link;
    }

    public String getURL(String baseUrl) {
        String link = "" + baseUrl +getValue();
        return link;
    }
}
