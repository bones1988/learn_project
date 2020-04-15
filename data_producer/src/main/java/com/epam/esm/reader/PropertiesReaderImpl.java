package com.epam.esm.reader;

import com.epam.esm.runner.DataProducerRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertiesReaderImpl implements PropertiesReader {

    @Override
    public Properties getProperties(String fileName) throws IOException {
        ClassLoader classLoader = DataProducerRunner.class.getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource(fileName)).getPath();
        InputStream input = new FileInputStream(path);
        try {
            Properties prop = new Properties();
            prop.load(input);
            return prop;
        } finally {
            input.close();
        }
    }
}
