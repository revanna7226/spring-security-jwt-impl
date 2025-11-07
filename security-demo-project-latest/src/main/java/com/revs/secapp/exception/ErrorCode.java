package com.revs.secapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(
        "USER_NOT_FOUND",
        "User not found with id %",
        HttpStatus.NOT_FOUND),
    CHANGE_PASSWORD_MISMATCH(
        "CHANGE_PASSWORD_MISMATCH",
        "New Password and Confirm Password are not matching",
        HttpStatus.BAD_REQUEST),
    INCORRECT_CURRENT_PASSWORD(
        "INCORRECT_CURRENT_PASSWORD",
        "Current Password is incorrect",
        HttpStatus.BAD_REQUEST),
    ACCOUNT_ALREADY_DEACTIVATED(
        "ACCOUNT_ALREADY_DEACTIVATED",
        "Account you are trying to deactivate is already deactivated!",
        HttpStatus.BAD_REQUEST),
    ACCOUNT_ALREADY_ACTIVATED(
        "ACCOUNT_ALREADY_ACTIVATED",
        "Account you are trying to activate is already activated!",
        HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(
        "EMAIL_ALREADY_EXISTS",
        "Email Already Exists",
        HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_ALREADY_EXISTS(
        "PHONE_NUMBER_ALREADY_EXISTS",
        "Phone number already exists",
        HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCHING(
        "PASSWORD_NOT_MATCHING",
        "Password and Confirm Passwords are not matching",
        HttpStatus.BAD_REQUEST),
    ERR_USER_DISABLED("ERR_USER_DISABLED",
        "User account is disabled, please activate your account or contact the administrator",
        UNAUTHORIZED),
    INVALID_CURRENT_PASSWORD("INVALID_CURRENT_PASSWORD", "The current password is incorrect", BAD_REQUEST),
    BAD_CREDENTIALS("BAD_CREDENTIALS", "Username and / or password is incorrect", UNAUTHORIZED),
    INTERNAL_EXCEPTION("INTERNAL_EXCEPTION",
        "An internal exception occurred, please try again or contact the admin",
        HttpStatus.INTERNAL_SERVER_ERROR),
    USERNAME_NOT_FOUND("USERNAME_NOT_FOUND", "Cannot find user with the provided username", NOT_FOUND),
    CATEGORY_ALREADY_EXISTS_FOR_USER("CATEGORY_ALREADY_EXISTS_FOR_USER", "Category already exists for this user", CONFLICT),
    ;

    private final String code;

    private final String defaultMessage;

    private final HttpStatus status;

    ErrorCode(String code, String defaultMessage, HttpStatus status) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.status = status;
    }
}
