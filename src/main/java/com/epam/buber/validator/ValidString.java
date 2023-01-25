package com.epam.buber.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidString {
    private ValidString() {
    }

    private static Matcher createMatcher(String regEx, String data) {
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(data);
        return matcher;
    }

    public static boolean isEmail(String data) {
        return createMatcher(ValidParameter.EMAIL_REGEX, data).matches();
    }

    public static boolean isPhoneNum(String data) {
        return createMatcher(ValidParameter.PHONE_NUM_REGEX, data).matches();
    }

    public static boolean isNameSurname(String data) {
        return createMatcher(ValidParameter.NAME_LASTNAME_REGEX, data).matches();
    }

    public static boolean isPassword(String data) {
        return createMatcher(ValidParameter.PASSWORD_REGEX, data).matches();
    }
}
