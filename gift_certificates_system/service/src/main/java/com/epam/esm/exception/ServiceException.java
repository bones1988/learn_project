package com.epam.esm.exception;

/**
 * class for service exceptions
 */
public abstract class ServiceException extends RuntimeException {
    private String parameter; //NOSONAR
    private ErrorCode errorCode; //NOSONAR

    public ServiceException(String parameter, ErrorCode errorCode) {
        this.parameter = parameter;
        this.errorCode = errorCode;
    }

    public ServiceException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getParameter() {
        return parameter;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
