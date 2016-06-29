package org.qbit.quiz.web.pages.quiz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.qbit.quiz.web.interceptor.Log;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by beniamin.czaplicki on 2016-06-17.
 */
@Named
@RequestScoped
@Log
public class AnswerPanel implements Serializable {

    private Logger logger = LogManager.getLogger(AnswerPanel.class);
    private static final long serialVersionUID = 1L;

    private String[] data = {"", "", "", "", ""};

    public AnswerPanel() {
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        logger.debug(Arrays.toString(data));
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserAnswer{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}
