package com.epam.buber.validator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringValidator {
    private static Logger logger = LogManager.getLogger();
    private StringValidator() {
    }

    private static Matcher createMatcher(String regEx, String data) {
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(data);
        return matcher;
    }

    public static boolean isEmail(String data) {
        logger.log(Level.INFO,"email: {}", createMatcher(ValidParameter.EMAIL_REGEX, data).matches());
        return createMatcher(ValidParameter.EMAIL_REGEX, data).matches();
    }

    public static boolean isPhoneNum(String data) {
        logger.log(Level.INFO,"phone: {}",createMatcher(ValidParameter.PHONE_NUM_REGEX, data).matches());
        return createMatcher(ValidParameter.PHONE_NUM_REGEX, data).matches();
    }

    public static boolean isNameSurname(String data) {
        logger.log(Level.INFO,"name: {}",createMatcher(ValidParameter.NAME_LASTNAME_REGEX, data).matches());
        return createMatcher(ValidParameter.NAME_LASTNAME_REGEX, data).matches();
    }

    public static boolean isPassword(String data) {
        logger.log(Level.INFO,"pass: {}", createMatcher(ValidParameter.PASSWORD_REGEX, data).matches());
        return createMatcher(ValidParameter.PASSWORD_REGEX, data).matches();
    }

    public static boolean isDrivingLic(String data){
        logger.log(Level.INFO,"driving lic: {}", createMatcher(ValidParameter.PASSWORD_REGEX, data).matches());
        return createMatcher(ValidParameter.PASSWORD_REGEX, data).matches();
    }

    public static boolean isDate(String o){
        logger.log(Level.INFO, o.toString());
        Date date = Date.valueOf(o);
        return true;
    }
}
