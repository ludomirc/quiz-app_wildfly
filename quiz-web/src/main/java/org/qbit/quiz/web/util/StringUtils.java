package org.qbit.quiz.web.util;

/**
 * Created by beniamin.czaplicki on 2016-06-17.
 */
public final class StringUtils {

    private StringUtils() {
    }

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
