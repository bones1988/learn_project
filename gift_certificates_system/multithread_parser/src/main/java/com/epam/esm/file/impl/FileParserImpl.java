package com.epam.esm.file.impl;

import com.epam.esm.file.FileParser;
import com.epam.esm.file.FileProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Scope("prototype")
public class FileParserImpl implements FileParser {
    private static final ReentrantLock locker = new ReentrantLock();
    private String path;
    private String errorFolder;
    private CountDownLatch countDownLatch;
    private List<Path> busyFiles;
    private static final ObjectMapper mapper = new ObjectMapper();
    private FileProcessor fileProcessor;

    @Autowired
    public FileParserImpl(FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void setErrorFolder(String errorFolder) {
        this.errorFolder = errorFolder;
    }

    @Override
    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            Path foundPath = findFile();
            Path newPath = renameFile(foundPath);
            fileProcessor.processFile(newPath, path);
        } catch (Exception e) {
            throw new RuntimeException("files not found");
        } finally {
            countDownLatch.countDown();
        }
    }

    @Override
    public Path renameFile(Path path) {
        File oldFile = new File(path.toString());
        String oldName = path.getFileName().toString();
        String folder = path.getParent().toString();
        StringBuilder sb = new StringBuilder(oldName);
        sb.insert(0, "~");
        String newName = folder + "\\" + sb.toString();
        File newFile = new File(newName);
        if (!oldFile.renameTo(newFile)) {
            throw new RuntimeException("Error renaming file");
        }
        return Paths.get(newName);
    }

    private Path findFile() throws IOException {
        try {
            locker.lock();
            Path foundPath = Files.walk(Paths.get(path))
                    .filter(folder -> !folder.toString().contains("error"))
                    .filter(Files::isRegularFile)
                    .filter(path1 -> !path1.toString().contains("~"))
                    .filter(path1 -> !busyFiles.contains(path1))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Files not found"));
            busyFiles.add(foundPath);
            return foundPath;
        } finally {
            locker.unlock();
        }
    }

    @Override
    public void setBusyFiles(List<Path> busyFiles) {
        this.busyFiles = busyFiles;
    }
}
