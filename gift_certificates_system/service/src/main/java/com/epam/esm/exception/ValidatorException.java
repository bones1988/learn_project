package com.epam.esm.exception;

/**
 * Class for exceptions connected with validation
 */
public class ValidatorException extends ServiceException {
    public ValidatorException(String parameter, ErrorCode errorCode) {
        super(parameter, errorCode);
    }
}
