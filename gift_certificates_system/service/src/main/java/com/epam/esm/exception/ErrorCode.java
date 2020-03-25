package com.epam.esm.exception;

/**
 * enum of possible exceptions
 */
public enum ErrorCode {
    //tags
    TAGS_NOT_FOUND(Codes.TAG_NOT_FOUND),
    NULL_INPUT_TAG(Codes.NULL_TAG_INPUT),
    INCORRECT_TAG_NAME(Codes.INCORRECT_TAG_NAME),
    TAGS_NOT_FOUND_BY_ID(Codes.TAG_NOT_FOUND_BY_ID),
    ERROR_DELETING_TAG(Codes.ERROR_DELETING_TAG),
    ERROR_SAVING_TAG(Codes.ERROR_SAVING_TAG),
    //certificates
    NULL_INPUT_CERTIFICATE(Codes.NULL_CERTIFICATE_INPUT),
    INCORRECT_CERTIFICATE_NAME(Codes.INCORRECT_CERTIFICATE_NAME),
    INCORRECT_CERTIFICATE_DESCRIPTION(Codes.INCORRECT_CERTIFICATE_DESCRIPTION),
    INCORRECT_CERTIFICATE_PRICE(Codes.INCORRECT_CERTIFICATE_PRICE),
    INCORRECT_CERTIFICATE_DURATION(Codes.INCORRECT_CERTIFICATE_DURATION),
    INCORRECT_TAGS_IN_CERTIFICATE(Codes.INCORRECT_TAGS_IN_CERTIFICATE),
    ERROR_DELETING_CERTIFICATE(Codes.ERROR_DELETING_CERTIFICATE),
    ERROR_UPDATING_CERTIFICATE(Codes.ERROR_UPDATING_CERTIFICATE),
    CERTIFICATE_NOT_FOUND(Codes.CERTIFICATE_NOT_FOUND),
    CERTIFICATE_NOT_FOUND_BY_ID(Codes.CERTIFICATE_NOT_FOUND_BY_ID),
    //users
    NULL_USER_INPUT(Codes.NULL_USER_INPUT),
    LOGIN_IS_PRESENT(Codes.LOGIN_IS_PRESENT),
    USER_BLOCKED(Codes.USER_BLOCKED),
    WRONG_LOGIN_OR_PASS(Codes.WRONG_LOGIN_OR_PASS),
    WRONG_FOR_LOGIN(Codes.WRONG_LOGIN_INPUT),
    WRONG_FOR_NAME(Codes.WRONG_NAME_INPUT),
    WRONG_FOR_PASS(Codes.WRONG_PASS_INPUT),
    WRONG_FOR_ROLE(Codes.WRONG_ROLE_INPUT),
    NO_USERS_FOUND(Codes.NO_USERS_FOUND),
    WRONG_SEARCH_PARAMS(Codes.WRONG_SEARCH_PARAMS),
    //external errors
    MISSING_PATH_VARIABLE(Codes.MISSING_PATH_VARIABLE),
    //search params
    INCORRECT_SORT_PARAM(Codes.INCORRECT_SORT_PARAM),
    WRONG_SEARCH_PARAM(Codes.WRONG_SEARCH_PARAM),
    //credentials
    NULL_CRED_INPUT(Codes.NULL_CRED_INPUT),
    WRONG_LOGIN_FOR_LOGIN(Codes.WRONG_LOGIN_FOR_LOGIN),
    WRONG_PASS_FOR_LOGIN(Codes.WRONG_PASS_FOR_LOGIN),
    //purchase
    DELETED_CERTIFICATE(Codes.DELETED_CERTIFICATE);
    private String errorCode; //NOSONAR


    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }


    private static class Codes {
        //tags
        private static final String NULL_TAG_INPUT = "100000";
        private static final String INCORRECT_TAG_NAME = "100001";
        private static final String ERROR_SAVING_TAG = "100012";
        private static final String TAG_NOT_FOUND = "100404";
        private static final String TAG_NOT_FOUND_BY_ID = "100405";
        private static final String ERROR_DELETING_TAG = "100013";

        //certificates
        private static final String NULL_CERTIFICATE_INPUT = "200000";
        private static final String INCORRECT_CERTIFICATE_NAME = "200001";
        private static final String INCORRECT_CERTIFICATE_DESCRIPTION = "200002";
        private static final String INCORRECT_CERTIFICATE_PRICE = "200003";
        private static final String INCORRECT_CERTIFICATE_DURATION = "200004";
        private static final String INCORRECT_TAGS_IN_CERTIFICATE = "200005";
        private static final String ERROR_DELETING_CERTIFICATE = "200013";
        private static final String ERROR_UPDATING_CERTIFICATE = "200011";
        private static final String CERTIFICATE_NOT_FOUND = "200404";
        private static final String CERTIFICATE_NOT_FOUND_BY_ID = "200405";

        //users
        private static final String NULL_USER_INPUT = "300000";
        private static final String LOGIN_IS_PRESENT = "300001";
        private static final String USER_BLOCKED = "300002";
        private static final String WRONG_LOGIN_OR_PASS = "300003";
        private static final String YOU_DO_NOT_HAVE_PERMISSION = "300004";//NOSONAR
        private static final String WRONG_LOGIN_INPUT = "300005";
        private static final String WRONG_NAME_INPUT = "300006";
        private static final String WRONG_PASS_INPUT = "300007";
        private static final String WRONG_ROLE_INPUT = "300008";
        private static final String NO_USERS_FOUND = "300009";
        private static final String WRONG_SEARCH_PARAMS = "300010";

        //external
        private static final String NOT_READABLE_MESSAGE = "400000";//NOSONAR
        private static final String METHOD_ARGUMENT_TYPE = "400001"; //NOSONAR
        private static final String MISSING_PATH_VARIABLE = "400002";

        //error
        private static final String SOMETHING_WENT_WRONG = "500000"; //NOSONAR

        //search params
        private static final String INCORRECT_SORT_PARAM = "600001";
        private static final String WRONG_SEARCH_PARAM = "600002";

        //credentials
        private static final String NULL_CRED_INPUT = "700000";
        private static final String WRONG_LOGIN_FOR_LOGIN = "700001";
        private static final String WRONG_PASS_FOR_LOGIN = "700002";

        //purchase
        private static final String DELETED_CERTIFICATE = "800000";
    }
}
