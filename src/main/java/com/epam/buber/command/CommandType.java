package com.epam.buber.command;

import com.epam.buber.command.impl.DefaultCommand;
import com.epam.buber.command.impl.AddUserCommand;
import com.epam.buber.command.impl.LoginCommand;
import com.epam.buber.command.impl.LogoutCommand;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum CommandType {
    ADD_USER(new AddUserCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    DEFAULT (new DefaultCommand());

    private static Logger logger = LogManager.getLogger();
    Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command define(String strCommand){
        CommandType currentComType = DEFAULT;
        try{
             currentComType = CommandType.valueOf(strCommand);
        } catch (IllegalArgumentException e){
            logger.log(Level.DEBUG, "Command name exception: {}", e.getMessage());
            return currentComType.command;
        }
        return currentComType.command;
    }
}
