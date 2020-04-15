package com.epam.esm.file;

import com.epam.esm.reader.PropertiesReader;
import com.epam.esm.reader.PropertiesReaderImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileCounterImpl implements FileCounter {
    private static PropertiesReader reader = new PropertiesReaderImpl();
    private String path;

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public long countErrorFiles() throws IOException {
        String errorName = reader.getProperties("data_producer.properties").getProperty("error");
        String errorFolderPath = path + "\\" + errorName;
        if (!Files.exists(Paths.get(errorFolderPath))) {
            return 0;
        } else {
            return Files.walk(Paths.get(path))
                    .filter(folder -> folder.toString().contains(errorName))
                    .filter(Files::isRegularFile).count();
        }
    }
}
