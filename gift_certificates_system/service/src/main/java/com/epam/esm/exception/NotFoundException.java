package com.epam.esm.exception;

/**
 * class for exceptions when something nit found
 */
public class NotFoundException extends ServiceException {
    public NotFoundException(String parameter, ErrorCode errorCode) {
        super(parameter, errorCode);
    }

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
