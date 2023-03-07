package com.epam.buber.validator;

import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.entity.types.UserRole;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class MapValidator {
    private static final String EMPTY_STRING = "";
    private static final String CHECK_PASS = "*";

    private MapValidator() {
    }

    public static boolean userFormValidate(Map<String, String> map) {
        boolean match = true;
        UserRole role = UserRole.define(map.get(RequestParameterName.USER_ROLE));
        if (!StringValidator.isEmail(map.get(RequestParameterName.EMAIL))) {
            map.replace(RequestParameterName.EMAIL, EMPTY_STRING);
            match = false;
        }
        if (!StringValidator.isPhoneNum(map.get(RequestParameterName.PHONE_NUM))) {
            map.replace(RequestParameterName.PHONE_NUM, EMPTY_STRING);
            match = false;
        }
        if (!StringValidator.isNameSurname(map.get(RequestParameterName.USER_NAME))) {
            map.replace(RequestParameterName.USER_NAME, EMPTY_STRING);
            match = false;
        }
        if (!StringValidator.isNameSurname(map.get(RequestParameterName.USER_LASTNAME))) {
            map.replace(RequestParameterName.USER_LASTNAME, EMPTY_STRING);
            match = false;
        }
        if (role.equals(UserRole.DRIVER)) {
            if (!StringValidator.isDrivingLic(map.get(RequestParameterName.DRIVER_LIC_NUMBER))) {
                map.replace(RequestParameterName.DRIVER_LIC_NUMBER, EMPTY_STRING);
                match = false;
            }
            Date licDate = Date.valueOf(map.get(RequestParameterName.DRIVER_LIC_VALID));
            Date currentDate = new Date(System.currentTimeMillis());
            if (!(StringValidator.isDate(map.get(RequestParameterName.DRIVER_LIC_VALID)) && licDate.after(currentDate))) {
                map.replace(RequestParameterName.DRIVER_LIC_VALID, EMPTY_STRING);
                match = false;
            }
        }
        if (!StringValidator.isPassword(map.get(RequestParameterName.PASSWORD)) || !map.get(RequestParameterName.PASSWORD).
                equals(map.get(RequestParameterName.PASSWORD_CHECK))) {
            map.replace(RequestParameterName.PASSWORD, EMPTY_STRING);
            map.replace(RequestParameterName.PASSWORD_CHECK, EMPTY_STRING);
            match = false;
        } else if (!match) {
            map.put(RequestParameterName.PASSWORD, CHECK_PASS);
            map.put(RequestParameterName.PASSWORD_CHECK, CHECK_PASS);
            match = false;
        }
        return match;
    }
}
