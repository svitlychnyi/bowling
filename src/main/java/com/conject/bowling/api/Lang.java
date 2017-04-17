package com.conject.bowling.api;

import com.conject.bowling.core.Language;
import com.conject.bowling.core.Messages;

import java.util.List;

/**
 * Created by gennadii on 01.07.16.
 */
public interface Lang {

    /**
     * Get list of supported languages
     * @return
     */
    List<Language> getSupportedLanguages();

    /**
     * Set default language used for displaying client-side messages
     * @param lang
     */
    void setLanguage(String lang);

    /**
     * Get Localized message using Message object
     * @param message, args
     * @return localized string
     */
    String getMessage(Messages message, String... args);

    /**
     * Get Localized message using String
     * @param message, args
     * @return localized string
     */
    String getMessage(String message, String... args);
}
