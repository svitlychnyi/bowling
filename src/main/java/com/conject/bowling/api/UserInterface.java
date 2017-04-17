package com.conject.bowling.api;

import com.conject.bowling.core.GameResults;
import com.conject.bowling.core.Messages;

/**
 * Created by gennadii on 01.07.16.
 */
public interface UserInterface {

    /**
     * Displays intermediate or final results to a User
     * @param results
     */
    void displayGameBoard(GameResults results);

    /**
     * Read from user a number of successfully resolved pins by one single hit
     * @return Integer
     */
    int getSingleResult();

    /**
     * Display a message to a user
     * @param message
     */
    void displayMessage(Messages message, String... args);
}
