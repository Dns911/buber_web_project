package com.epam.buber.validator;

import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.entity.parameter.UserRole;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.HashMap;

public class MapValidator {
    private static Logger logger = LogManager.getLogger();

    private MapValidator() {
    }

    public static boolean userFormValid(HashMap<String, Object> map) {
        boolean match = true;
        UserRole role = UserRole.define(map.get(RequestParameterName.USER_ROLE).toString());
        if (!StringValidator.isEmail(map.get(RequestParameterName.EMAIL).toString())) {
            map.replace(RequestParameterName.EMAIL, "");
            match = false;
        }
        if (!StringValidator.isPhoneNum(map.get(RequestParameterName.PHONE_NUM).toString())) {
            map.replace(RequestParameterName.PHONE_NUM, "");
            match = false;
        }
        if (!StringValidator.isNameSurname(map.get(RequestParameterName.USER_NAME).toString())) {
            map.replace(RequestParameterName.USER_NAME, "");
            match = false;
        }
        if (!StringValidator.isNameSurname(map.get(RequestParameterName.USER_LASTNAME).toString())) {
            map.replace(RequestParameterName.USER_LASTNAME, "");
            match = false;
        }
        //----for driver
        if (role.equals(UserRole.DRIVER)) {
            if (!StringValidator.isDrivingLic(map.get(RequestParameterName.DRIVER_LIC_NUMBER).toString())) {
                map.replace(RequestParameterName.DRIVER_LIC_NUMBER, "");
                match = false;
            }
            Date licDate = Date.valueOf(map.get(RequestParameterName.DRIVER_LIC_VALID).toString());
            Date currentDate = new Date(System.currentTimeMillis());
            if (!(StringValidator.isDate(map.get(RequestParameterName.DRIVER_LIC_VALID).toString()) && licDate.after(currentDate))) {
                map.replace(RequestParameterName.DRIVER_LIC_VALID, "");
                match = false;
            }
        }
        // ----
        if (!StringValidator.isPassword(map.get(RequestParameterName.PASSWORD).toString()) || !map.get(RequestParameterName.PASSWORD).
                equals(map.get(RequestParameterName.PASSWORD_CHECK))) {
            map.replace(RequestParameterName.PASSWORD, "");
            map.replace(RequestParameterName.PASSWORD_CHECK, "");
            match = false;
        } else if (!match) {
            map.put(RequestParameterName.PASSWORD, "*");
            map.put(RequestParameterName.PASSWORD_CHECK, "*");
            match = false;
        }
        logger.log(Level.INFO, "valid form: " + match);
        return match;
    }
}
