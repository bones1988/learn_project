package com.epam.esm.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Class for running application
 */
@SpringBootApplication
public class SpringBootRunner {

    private static final String CLASSPATH = "classpath:errormesages/errormessages";
    private static final String ENCODING = "UTF-8";

    /**
     * Main running method of application
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringBootRunner.class, args);
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
