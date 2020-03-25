package com.epam.esm.exception.errorentity;

/**
 * Class of entity with message and code for errors
 */
public class ErrorEntity {
    private String code;
    private String message;

    public ErrorEntity(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code of error
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message exception message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
