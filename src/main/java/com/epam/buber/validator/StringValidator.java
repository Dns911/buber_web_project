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
        return createMatcher(RegexValidator.EMAIL_REGEX, data).matches();
    }

    public static boolean isPhoneNum(String data) {
        return createMatcher(RegexValidator.PHONE_NUM_REGEX, data).matches();
    }

    public static boolean isNameSurname(String data) {
        return createMatcher(RegexValidator.NAME_LASTNAME_REGEX, data).matches();
    }

    public static boolean isPassword(String data) {
        return createMatcher(RegexValidator.PASSWORD_REGEX, data).matches();
    }

    public static boolean isDrivingLic(String data) {
        return createMatcher(RegexValidator.ID_DRIVING_LIC, data).matches();
    }

    public static boolean isDate(String data) {
        return createMatcher(RegexValidator.DATE_REGEX, data).matches();
    }

    public static boolean isCarId(String data) {
        return createMatcher(RegexValidator.ID_CAR, data).matches();
    }
}
