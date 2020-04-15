package com.epam.esm.reader;

import java.io.IOException;
import java.util.Properties;

public interface PropertiesReader {
    Properties getProperties(String fileName) throws IOException;
}
