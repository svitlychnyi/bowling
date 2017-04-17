package com.conject.bowling.core;

/**
 * Created by gennadii on 06.07.16.
 */
public enum Language {
    EN("en", "message.english"),
    DE("de", "message.german");

    private String key;
    private String name;
    Language(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return this.key;
    }

    public String getName() {
        return this.name;
    }
}
