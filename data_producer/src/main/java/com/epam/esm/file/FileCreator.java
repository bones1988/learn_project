package com.epam.esm.file;

import com.epam.esm.certificate.CertificateGenerator;
import com.epam.esm.certificate.CertificateGeneratorImpl;
import com.epam.esm.certificate.GiftCertificate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class FileCreator implements Runnable {
    private static final int VALID_FILES = 16;
    private Path path;
    private int filesCount;
    private static transient int counter = 0;
    private static CertificateGenerator certificateGenerator;
    private static NameGenerator nameGenerator = NameGenerator.getInstance();
    private static int badFilesCount = 0;
    private static final ReentrantLock lock = new ReentrantLock();
    private static final String WIP_SIGN = "~";

    public FileCreator(Path path, int filesCount) {
        this.path = path;
        this.filesCount = filesCount;
        certificateGenerator = new CertificateGeneratorImpl();
    }

    @Override
    public void run() {
        int thisCount = counter;
        try {
            for (int i = 0; i < filesCount; i++) {
                int name = nameGenerator.generateNameNumber();
                Path filePath = path.resolve(WIP_SIGN + name + ".txt");
                List<GiftCertificate> certificateList = certificateGenerator.generateCertificates(String.valueOf(name));
                if (thisCount % (VALID_FILES + 1) == 0 && thisCount != 0) {
                    generateIncorrectFiles();
                    continue;
                }
                writeFile(filePath, certificateList);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error running " + e.getMessage());
        }
    }

    private void generateIncorrectFiles() throws IOException {
        lock.lock();
        try {
            badFilesCount = badFilesCount + 4;
        } finally {
            lock.unlock();
        }
        int name = nameGenerator.generateNameNumber();
        Path filePath = path.resolve(WIP_SIGN + "bad_json_" + name + ".txt");
        String wrongJson = certificateGenerator.generateIncorrectJson();
        writeFile(filePath, wrongJson);
        name = nameGenerator.generateNameNumber();
        filePath = path.resolve(WIP_SIGN + "bad_field_" + nameGenerator.generateNameNumber() + ".txt");
        List<GiftCertificate> wrongCertificates = certificateGenerator.generateIncorrectFieldName(String.valueOf(name));
        writeFile(filePath, wrongCertificates);
        name = nameGenerator.generateNameNumber();
        filePath = path.resolve(WIP_SIGN + "bad_bean_" + nameGenerator.generateNameNumber() + ".txt");
        List<GiftCertificate> certificateList = certificateGenerator.generateCertificateJsonWithNonValidBean(String.valueOf(name));
        writeFile(filePath, certificateList);
        name = nameGenerator.generateNameNumber();
        filePath = path.resolve(WIP_SIGN + "bad_value_" + nameGenerator.generateNameNumber() + ".txt");
        certificateList = certificateGenerator.generateCertificatesWithDbConstraints(String.valueOf(name));
        writeFile(filePath, certificateList);
    }

    private void writeFile(Path path, Object data) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        OutputStream ous = Files.newOutputStream(path);
        objectMapper.writeValue(ous, data);
        ous.close();
        File newFileName = new File(path.toString().replaceAll(WIP_SIGN, ""));
        File file = new File(path.toString());
        if (!file.renameTo(newFileName)) {
            throw new RuntimeException("Error rename file!");
        }
        increment();
    }

    public static int getCounter() {
        return counter;
    }

    public static synchronized void increment() {
        counter++;
    }

    public static int getBadFilesCount() {
        return badFilesCount;
    }
}
