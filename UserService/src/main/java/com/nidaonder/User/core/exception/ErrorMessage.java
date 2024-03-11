package com.nidaonder.User.core.exception;

import lombok.Getter;

@Getter
public enum ErrorMessage implements BaseErrorMessage {

    ITEM_NOT_FOUND("Item not found!"),
    ITEM_ALREADY_EXIST("Item exist!"),
    WRONG_PASSWORD("The current password is incorrect!"),
    NEW_PASSWORD_CANNOT_BE_SAME("The new password must be different from the current password!"),
    NEW_PASSWORDS_DO_NOT_MATCH("The new passwords do not match!");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
