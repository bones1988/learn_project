package com.epam.esm.exception.handler;

import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.exception.errorentity.ErrorEntity;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

/**
 * Class for handling exceptions
 */
@RestControllerAdvice
public class ShopExceptionHandler extends ResponseEntityExceptionHandler {
    private MessageSource messageSource;

    @Autowired
    public ShopExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private ErrorEntity makeMessage(String errorCode, String parameter) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage(
                errorCode, new Object[]{}, locale) + parameter;
        return new ErrorEntity(errorCode, errorMessage);
    }

    /**
     * @param e exception
     * @return entity with message
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorEntity handleNotFoundException(NotFoundException e) {
        logger.error(e);
        return makeMessage(e.getErrorCode().getErrorCode(), e.getParameter());
    }

    /**
     * @param e exception
     * @return entity with message
     */
    @ExceptionHandler(ValidatorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorEntity handleBadInputException(ValidatorException e) {
        logger.error(e);
        return makeMessage(e.getErrorCode().getErrorCode(), e.getParameter());
    }

    /**
     * @param e exception
     * @return entity with message
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorEntity handleAccessDeniedException(AccessDeniedException e) {
        logger.error(e);
        return makeMessage("300004", "");
    }

    /**
     * @param e exception
     * @return entity with message
     */
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorEntity handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        logger.error(e);
        return makeMessage("300003", "");
    }

    /**
     * @param e exception
     * @return entity with message
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorEntity handleError(Exception e) {
        logger.error(e);
        return makeMessage("500000", "");
    }

    /**
     * @param ex exception
     * @return entity with message
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(ex);
        return ResponseEntity
                .status(status)
                .body(makeMessage("400000", ""));
    }

    /**
     * @param ex exception
     * @return entity with message
     */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(ex);
        return ResponseEntity
                .status(status)
                .body(makeMessage("400001", ""));
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(ex);
        return ResponseEntity
                .status(status)
                .body(makeMessage("500001", ""));
    }
}
