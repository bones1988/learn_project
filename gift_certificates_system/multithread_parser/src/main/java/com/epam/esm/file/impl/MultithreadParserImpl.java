package com.epam.esm.file.impl;

import com.epam.esm.file.MultithreadParser;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("parser.properties")
@Component
public class MultithreadParserImpl implements MultithreadParser {
    @Value("${folder}")
    private String path;
    @Value("${threads}")
    private int threadsCount;
    @Value("${delay}")
    private double scanDelay;
    @Value("${error}")
    private String errorFolder;
    @Autowired
    private GiftCertificateService giftCertificateService;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void process() throws IOException {
        Path errorFolderPath = Paths.get(path + "\\" + errorFolder);
        if (!Files.exists(errorFolderPath)) {
            Files.createDirectory(errorFolderPath);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);
        ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor();
        long delay = Math.round(scanDelay * 1000);
        scheduledService.scheduleWithFixedDelay(() -> {
            CountDownLatch latch = new CountDownLatch(threadsCount);
            List<Path> busyFiles = new ArrayList<>();
            for (int i = 0; i < threadsCount; i++) {
                FileParserImpl fileParser = applicationContext.getBean(FileParserImpl.class);
                fileParser.setPath(path);
                fileParser.setErrorFolder(errorFolder);
                fileParser.setBusyFiles(busyFiles);
                fileParser.setCountDownLatch(latch);
                executorService.submit(fileParser);
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException("Error while restarting job");
            }
        }, delay, delay, TimeUnit.MILLISECONDS);
    }
}
