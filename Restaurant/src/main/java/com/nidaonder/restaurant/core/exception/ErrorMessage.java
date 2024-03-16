package com.nidaonder.restaurant.core.exception;

import lombok.Getter;

@Getter
public enum ErrorMessage implements BaseErrorMessage {
    ITEM_NOT_FOUND("Item not found!"),
    ITEM_ALREADY_EXIST("Item exist!"),
    SOMETHING_WENT_WRONG("Something went wrong!!!");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
