package com.epam.esm.file;

public class NameGenerator {
    private static final NameGenerator instance = new NameGenerator();
    private static int nameNumber = 0;

    private NameGenerator() {
    }

    public static NameGenerator getInstance() {
        return instance;
    }

    public int generateNameNumber() {
        int result = nameNumber;
        nameNumber++;
        return result;
    }
}
