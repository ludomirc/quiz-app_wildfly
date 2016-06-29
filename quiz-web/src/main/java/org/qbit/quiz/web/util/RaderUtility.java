package org.qbit.quiz.web.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by beniamin.czaplicki on 2016-06-08.
 */

public class RaderUtility {

    private static Logger logger = LogManager.getLogger(RaderUtility.class);

    public static ByteArrayOutputStream getByteArrayOutputStream(InputStream is) {
        byte[] buffer = new byte[512];
        int bytesRead = -1;
        logger.debug("Receiving data...");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            while ((bytesRead = is.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            logger.error(e);
        }
        logger.debug("Receiving data size: "  + bos.size()/8 + " kb");

        return bos;
    }
}
