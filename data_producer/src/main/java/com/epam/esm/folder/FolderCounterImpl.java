package com.epam.esm.folder;

import com.epam.esm.reader.PropertiesReader;
import com.epam.esm.reader.PropertiesReaderImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FolderCounterImpl implements FolderCounter {
    private static PropertiesReader reader = new PropertiesReaderImpl();

    @Override
    public List<Path> countFolders(String path) throws IOException {
        String errorFolderPath = reader.getProperties("data_producer.properties").getProperty("error");
        return Files.walk(Paths.get(path))
                .filter(folder -> !folder.toString().contains(errorFolderPath))
                .filter(Files::isDirectory)
                .collect(Collectors.toList());
    }
}
