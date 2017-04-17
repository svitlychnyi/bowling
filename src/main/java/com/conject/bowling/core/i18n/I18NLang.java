package com.conject.bowling.core.i18n;

import com.conject.bowling.api.Lang;
import com.conject.bowling.core.Language;
import com.conject.bowling.core.Messages;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by gennadii on 01.07.16.
 */
@Component
public class I18NLang implements Lang {
    private static final String BUNDLE_LOCATION = "messages/messages";
    private Locale currentLocale = new Locale(Language.EN.getKey());
    private ResourceBundle messages = ResourceBundle.getBundle(BUNDLE_LOCATION, currentLocale);

    @Override
    public List<Language> getSupportedLanguages() {
        return new ArrayList<>(Arrays.asList(Language.values()));
    }

    @Override
    public void setLanguage(String lang) {
        this.currentLocale = new Locale(lang);
        this.messages = ResourceBundle.getBundle(BUNDLE_LOCATION, currentLocale);
    }

    @Override
    public String getMessage(Messages message, String... args) {
        return this.getMessage(message.getKey(), args);
    }

    @Override
    public String getMessage(String message, String... args) {
        return String.format(this.messages.getString(message), args);
    }
}
