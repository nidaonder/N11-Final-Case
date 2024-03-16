package com.nidaonder.User.core.exception;

import lombok.Getter;

@Getter
public enum ErrorMessage implements BaseErrorMessage {

    ITEM_NOT_FOUND("Item not found!"),
    USER_NOT_FOUND("User not found!"),
    USER_REVIEW_NOT_FOUND("User review not found!"),
    RESTAURANT_NOT_FOUND("Restaurant not found!"),
    ITEM_ALREADY_EXIST("Item already exist!"),
    USER_ALREADY_EXIST("User already exist!"),
    WRONG_PASSWORD("The current password is incorrect!"),
    NEW_PASSWORD_CANNOT_BE_SAME("The new password must be different from the current password!"),
    NEW_PASSWORDS_DO_NOT_MATCH("The new passwords do not match!"),
    SOMETHING_WENT_WRONG("Something went wrong while connecting restaurant service!"),
    EMAIL_ALREADY_IN_USE("This email is already in use!");


    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
