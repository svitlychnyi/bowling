package com.conject.bowling.ui.console;

import com.conject.bowling.api.Lang;
import com.conject.bowling.api.UserInterface;
import com.conject.bowling.core.*;
import org.apache.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Created by sargon on 7/2/16.
 */
public class ConsoleUserInterface implements UserInterface {
    private static final Logger logger = Logger.getLogger(ConsoleUserInterface.class);
    private Lang lang;
    private static Scanner in = new Scanner(System.in, StandardCharsets.UTF_8.toString());
    private static final String QUITE_STRING = "q";

    public ConsoleUserInterface(Lang lang) {
        this.lang = lang;
    }

    @Override
    public void displayGameBoard(GameResults results) {
        StringBuilder stringBuilder;
        for (String user : results.getUsers()) {
            stringBuilder = new StringBuilder(8);
            String resultOutput = stringBuilder.append("| ")
                    .append(user)
                    .append(" || ")
                    .append(results.getUserResults(user).toString()).toString();

            stringBuilder = new StringBuilder(8);
            int i = 0;
            while (i < resultOutput.length()) {
                stringBuilder.append('-');
                i++;
            }
            System.out.println(stringBuilder.toString());
            System.out.println(resultOutput);
            System.out.println(stringBuilder.toString());
        }
    }

    @Override
    public int getSingleResult() {
        this.displayMessage(Messages.GET_SINGLE_RESULT);
        boolean isValidValue = true;
        Integer result = null;
        String value;
        try {
            value = in.next();
            if(value.equals(QUITE_STRING)) {
                logger.info("User terminated the game by entering quit string: " + QUITE_STRING);
                displayMessage(Messages.GOODBYE);
                Runtime.getRuntime().exit(0);
            }

            result = Integer.valueOf(value);
            if (result < 0 || result > 10) {
                isValidValue = false;
            }
        }
        catch (NumberFormatException th) {
            logger.error("Failed to read valid value from user", th);
        }

        if (result != null && isValidValue) {
            return result;
        } else {
            this.displayMessage(Messages.VALUE_IS_NOT_VALID);
            return getSingleResult();
        }
    }

    @Override
    public void displayMessage(Messages message, String... args) {

        logger.debug(new StringBuilder(8)
                .append("Displaying message to user: ")
                .append(message.getKey())
                .toString());

        System.out.println(lang.getMessage(message, args));
    }
}
