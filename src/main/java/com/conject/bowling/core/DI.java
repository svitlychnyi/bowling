package com.conject.bowling.core;

import com.conject.bowling.api.Game;
import com.conject.bowling.api.Lang;
import com.conject.bowling.api.UserInterface;
import com.conject.bowling.core.i18n.I18NLang;
import com.conject.bowling.game.singleplayer.SinglePlayerGame;
import com.conject.bowling.ui.console.ConsoleUserInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Main DI Configuration
 * Created by gennadii on 07.07.16.
 */
@Configuration
@ComponentScan(value={"com.conject.bowling"})
public class DI {

    @Bean
    public UserInterface getUserInterface(){
        return new ConsoleUserInterface(getLang());
    }

    @Bean
    public Game getGame(){
        return new SinglePlayerGame(getUserInterface());
    }

    @Bean
    Lang getLang() {
        return new I18NLang();
    }
}