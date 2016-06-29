package org.qbit.quiz.model;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

public class IdGenerator {

    public static String createId() {
        UUID uuid = Generators.timeBasedGenerator().generate();
        uuid.timestamp();

        return  uuid.toString();
    }
}
