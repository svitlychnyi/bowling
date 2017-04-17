package com.conject.bowling.game.singleplayer;

import com.conject.bowling.api.Game;
import com.conject.bowling.api.UserInterface;
import com.conject.bowling.core.GameResults;
import com.conject.bowling.core.Messages;
import com.conject.bowling.core.Results;
import org.apache.log4j.Logger;

/**
 * Created by gennadii on 01.07.16.
 */
public class SinglePlayerGame implements Game {
    private static final Logger logger = Logger.getLogger(SinglePlayerGame.class);
    private UserInterface ui;
    private static final String USER_NAME = "user";

    public SinglePlayerGame(UserInterface ui) {
        this.ui = ui;
    }

    @Override
    public void play() {
        logger.info("Let the game begin");
        ui.displayMessage(Messages.WELCOME);

        GameResults gameResults = new GameResults();
        Results results;

        int nextSingleResult;
        while(! gameResults.isComplete()) {
            results = gameResults.getUserResults(USER_NAME);
            nextSingleResult = ui.getSingleResult();

            if( ! results.isNextHitValid(nextSingleResult)) {
                ui.displayMessage(Messages.VALUE_IS_NOT_VALID);
                continue;
            }

            results.addHit(nextSingleResult);
            ui.displayGameBoard(gameResults);
        }

        logger.info("Game over.");
        ui.displayMessage(Messages.GOODBYE);
    }
}
