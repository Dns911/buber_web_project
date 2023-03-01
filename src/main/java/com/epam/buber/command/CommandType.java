package com.epam.buber.command;

import com.epam.buber.command.impl.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum CommandType {
    ADD_USER(new AddUserCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTRATION(new RegistrationPageCommand()),
    GOTO_MAIN(new GotoMainCommand()),
    PREORDER(new PreOrderCommand()),
    ORDER_START(new OrderStartCommand()),
    ORDER_FINISH(new OrderFinishCommand()),
    ORDER_INFO(new OrderInfoCommand()),
    USER_PAGE(new UserPageCommand()),
    USER_INFO(new UserInfoCommand()),
    RESTORE_PASS(new RestorePassCommand()),
    RESTORE_PAGE(new RestorePageCommand()),
    DRIVER_SHIFT_START(new DriverShiftStartCommand()),
    DRIVER_SHIFT_END(new DriverShiftEndCommand()),
    DEFAULT(new DefaultCommand());

    private static Logger logger = LogManager.getLogger();
    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command define(String strCommand) {
        CommandType currentComType = DEFAULT;
        try {
            currentComType = CommandType.valueOf(strCommand);
        } catch (IllegalArgumentException e) {
            logger.log(Level.DEBUG, "Command name exception: {}", e.getMessage());
            return currentComType.command;
        }
        return currentComType.command;
    }

    @Override
    public String toString() {
        return this.command.toString();
    }
}
