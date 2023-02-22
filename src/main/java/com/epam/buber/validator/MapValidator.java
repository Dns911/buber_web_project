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

    public static boolean userFormValid(HashMap<String, String> map){
        boolean match = true;
        UserRole role = UserRole.define(map.get(RequestParameterName.USER_ROLE));
        if (!StringValidator.isEmail(map.get(RequestParameterName.EMAIL))){
            map.replace(RequestParameterName.EMAIL, "");
            match = false;
        }

        if (!StringValidator.isPhoneNum(map.get(RequestParameterName.PHONE_NUM))){
            map.replace(RequestParameterName.PHONE_NUM, "");
            match = false;
        }
        if (!StringValidator.isNameSurname(map.get(RequestParameterName.USER_NAME))){
            map.replace(RequestParameterName.USER_NAME, "");
            match = false;
        }
        if (!StringValidator.isNameSurname(map.get(RequestParameterName.USER_LASTNAME))){
            map.replace(RequestParameterName.USER_LASTNAME, "");
            match = false;
        }
        //----for driver
        if (role.equals(UserRole.DRIVER)){
            if (!StringValidator.isDrivingLic(map.get(RequestParameterName.DRIVER_LIC_NUMBER))){
                map.replace(RequestParameterName.DRIVER_LIC_NUMBER, "");
                match = false;
            }
            Date licDate = Date.valueOf(map.get(RequestParameterName.DRIVER_LIC_VALID));
            Date currentDate = new Date(System.currentTimeMillis());
            if (!(StringValidator.isDate(map.get(RequestParameterName.DRIVER_LIC_VALID)) && licDate.after(currentDate))){
                map.replace(RequestParameterName.DRIVER_LIC_VALID, "");
                match = false;
            }
        }
       // ----
        if (!StringValidator.isPassword(map.get(RequestParameterName.PASSWORD)) || !map.get(RequestParameterName.PASSWORD).
                equals(map.get(RequestParameterName.PASSWORD_CHECK))){
            map.replace(RequestParameterName.PASSWORD, "");
            map.replace(RequestParameterName.PASSWORD_CHECK, "");
            match = false;
        } else if (!match ) {
            map.put(RequestParameterName.PASSWORD, "*");
            map.put(RequestParameterName.PASSWORD_CHECK, "*");
            match = false;
        }
        logger.log(Level.INFO, "valid form: "+match);
        return match;
    }
}
