package com.conject.bowling.core;

/**
 * Created by gennadii on 01.07.16.
 */
public enum Messages {
    WELCOME("message.welcome"),
    GOODBYE("message.goodbye"),
    GET_SINGLE_RESULT("message.give_single_result"),

    VALUE_IS_NOT_VALID("error.value_is_not_valid");


    private String key;
    Messages(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
