package com.epam.esm.runner;

import com.epam.esm.database.CertificateCounter;
import com.epam.esm.database.CertificateCounterImpl;
import com.epam.esm.file.FileCounter;
import com.epam.esm.file.FileCounterImpl;
import com.epam.esm.file.FileCreator;
import com.epam.esm.folder.FolderCounter;
import com.epam.esm.folder.FolderCounterImpl;
import com.epam.esm.folder.FolderCreator;
import com.epam.esm.folder.FolderCreatorImpl;
import com.epam.esm.reader.PropertiesReader;
import com.epam.esm.reader.PropertiesReaderImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DataProducerRunner {
    private static Logger logger = LogManager.getLogger(DataProducerRunner.class);

    public static void main(String[] args) {
        try {
            PropertiesReader reader = new PropertiesReaderImpl();
            Properties properties = reader.getProperties("data_producer.properties");
            assert properties != null;
            CertificateCounter certificateCounter = new CertificateCounterImpl();
            certificateCounter.setUrl(properties.getProperty("url"));
            certificateCounter.setUser(properties.getProperty("username"));
            certificateCounter.setPassword(properties.getProperty("password"));
            long startCertificatesCount = certificateCounter.getCertificatesCount();
            String folder = properties.getProperty("folder");
            FileCounter fileCounter = new FileCounterImpl();
            fileCounter.setPath(folder);
            long startErrorFiles = fileCounter.countErrorFiles();
            String SUBFOLDERS_COUNT = properties.getProperty("SUBFOLDERS_COUNT");
            int subFoldersCount = Integer.parseInt(SUBFOLDERS_COUNT);
            FolderCreator creator = new FolderCreatorImpl();
            creator.createDirs(folder, subFoldersCount);
            FolderCounter folderCounter = new FolderCounterImpl();
            List<Path> folders = folderCounter.countFolders(folder);
            int filesCount = Integer.parseInt(properties.getProperty("files_count"));
            int testTime = Integer.parseInt(properties.getProperty("test_time"));
            double period = Double.parseDouble(properties.getProperty("period"));
            List<Thread> allThreads = new ArrayList<>();
            logger.info("Start generating files");
            for (double i = 0; i < testTime; i += period) {
                List<Thread> threadList = new ArrayList<>();
                folders.forEach(path -> {
                    FileCreator fileCreator = new FileCreator(path, filesCount);
                    Thread thread = new Thread(fileCreator);
                    threadList.add(thread);
                    allThreads.add(thread);
                });
                for (Thread thread : threadList) {
                    thread.start();
                }
                Thread.sleep((long) (period * 1000));
            }
            for (Thread thread : allThreads) {
                thread.join();
            }
            logger.info("Files generated, waiting for process");
            long actualCertificatesCount = certificateCounter.getCertificatesCount();
            long actualErrorFiles = fileCounter.countErrorFiles();
            long expectedCertificates = startCertificatesCount + (FileCreator.getCounter() - FileCreator.getBadFilesCount()) * 3;
            long expectedErrorFiles = startErrorFiles + FileCreator.getBadFilesCount();
            while (actualCertificatesCount != expectedCertificates && expectedErrorFiles != actualErrorFiles) {
                long previosCertificatesCount = actualCertificatesCount;
                long prevoisFilesCount = actualErrorFiles;
                actualCertificatesCount = certificateCounter.getCertificatesCount();
                actualErrorFiles = fileCounter.countErrorFiles();
                if (actualCertificatesCount == previosCertificatesCount && actualErrorFiles == prevoisFilesCount) {
                    throw new RuntimeException("Process finished while there was something to process");
                }
                Thread.sleep(5000);
            }
            logger.info("Start error files in folder: " + startErrorFiles);
            logger.info("Expected count of error files: " + (startErrorFiles + FileCreator.getBadFilesCount()) +
                    " actual is: " + actualErrorFiles);
            logger.info("Start count of certificates: " + startCertificatesCount);
            logger.info("expected count of certificates: " + expectedCertificates +
                    " actual is: " + actualCertificatesCount);
            logger.info("All processed correct!");
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
