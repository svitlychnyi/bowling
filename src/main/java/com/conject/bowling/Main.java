package com.conject.bowling;

import com.conject.bowling.api.Game;
import com.conject.bowling.core.DI;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Main Class for the Bowling game
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DI.class);

        Game game = context.getBean(Game.class);
        game.play();

        context.close();
    }
}
