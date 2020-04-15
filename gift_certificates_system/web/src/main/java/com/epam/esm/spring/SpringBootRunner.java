package com.epam.esm.spring;

import com.epam.esm.file.MultithreadParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.io.IOException;

/**
 * Class for running application
 */
@SpringBootApplication
public class SpringBootRunner {

    private static final String CLASSPATH = "classpath:errormesages/errormessages";
    private static final String ENCODING = "UTF-8";
    @Autowired
    private MultithreadParser multithreadParser;


    /**
     * Main running method of application
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringBootRunner.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startMultithreadParser() throws IOException {
        multithreadParser.process();
    }

    /**
     * Method for creating message source bean
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(CLASSPATH);
        messageSource.setDefaultEncoding(ENCODING);
        return messageSource;
    }
}
