package com.bogdan.webapp;

import org.springframework.http.HttpStatus;

public enum ErrorsEnum {

    GENERAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error.", 1),
    STUDENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Student was not found", 2),
    STUDENT_EXISTS(HttpStatus.NOT_ACCEPTABLE, "Student already found", 3),
    TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "Token is missing from request", 4),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "Token is invalid", 5),
    LOGIN_WRONG_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Wrong username or password", 6);

    private HttpStatus httpStatus;

    private String errorDescription;

    private int errorCode;

    ErrorsEnum(HttpStatus httpStatus, String errorDescription, int errorCode) {
        this.httpStatus = httpStatus;
        this.errorDescription = errorDescription;
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

}
