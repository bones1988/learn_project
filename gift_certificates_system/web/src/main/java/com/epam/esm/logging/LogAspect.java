package com.epam.esm.logging;

import com.epam.esm.dto.impl.GiftCertificateDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Class for logging
 */
@Aspect
@Component
public class LogAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    /**
     * pointcut for beans
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Pointcut("execution(public * com.epam.esm.service.impl.GiftCertificateServiceImpl.save(..))")
    public void certificateProcess() {
    }

        @Around("certificateProcess()")
    public void logCertificateProcess(ProceedingJoinPoint jp) throws Throwable {
        startDate = LocalDateTime.now();
        GiftCertificateDto certificateDto = (GiftCertificateDto)jp.getArgs()[0];
        String processedCertificate = certificateDto.getName();
        Object result = jp.proceed();
        endDate = LocalDateTime.now();
        Duration duration = Duration.between(startDate, endDate);
        long latency = duration.getNano();
        double milliseconds = latency/(1000000*1.0);
        log.warn("Processed certificate :" + processedCertificate + " for " + milliseconds + " miliseconds.");
    }


    @Pointcut("execution(public * com.epam.esm.file.FileProcessor.processFile(..))")
    public void fileProcess() {
    }

    @AfterThrowing(pointcut = "fileProcess()", throwing = "e")
    public void logError(JoinPoint jp, Throwable e) {
        log.error("Processing failure");
        log.error(String.valueOf(e.getClass()));
        log.error(e.getMessage());
        log.error(Arrays.toString(e.getStackTrace()));
    }

    @Around("fileProcess()")
    public void logFileProcess(ProceedingJoinPoint jp) throws Throwable {
        startDate = LocalDateTime.now();
        String processedFile = jp.getArgs()[0].toString().replaceAll("~", "");
        Object result = jp.proceed();
        endDate = LocalDateTime.now();
        Duration duration = Duration.between(startDate, endDate);
        long latency = duration.getNano();
        double milliseconds = latency/(1000000*1.0);
        String resultValue;
        if ((boolean) result) {
            resultValue = "success!";
        } else {
            resultValue = "failure!";
        }
        log.warn("Processed file :" + processedFile + " for " + milliseconds + " miliseconds with " + resultValue);
    }

    /**
     * @param joinPoint point
     * @param e         exception
     */
    @AfterThrowing(pointcut = "springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
    }

    /**
     * @param joinPoint point
     * @return object of log around
     * @throws Throwable exceptions
     */
    @Around("springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()) {
            log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }
}
