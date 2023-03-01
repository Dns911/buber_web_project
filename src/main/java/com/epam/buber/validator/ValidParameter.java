package com.epam.buber.validator;

public class ValidParameter {
    private ValidParameter() {
    }

    public static final String EMAIL_REGEX = "[A-za-z0-9_.-]{2,20}@[a-z]{2,10}[.]?[a-z]{2,4}";
    public static final String PHONE_NUM_REGEX = "375((29)|(33)|(44))[0-9]{7}";
    public static final String NAME_LASTNAME_REGEX = "([А-Яа-я]{2,12}[-]?[А-Яа-я]{2,10})|([A-za-z]{2,12}[-]?[A-za-z]{2,10})";
    public static final String DATE_REGEX = ".";
    public static final String PASSWORD_REGEX = ".{4,20}";
    //    public static final String PASSWORD_REGEX = "((?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[-=+*!@%^&?'\"]).{6,20})";
    public static final String ID_CAR = "[0-9]{4}[ABCEHIKMOPTX]{2}-?[1-7]";
    public static final String ID_DRIVING_LIC = "[0-9][ABCEHIKMOPTX]{2}[0-9]{7}"; //0000AA-0
    public static final String DATE = "[0-9][ABCEHIKMOPTX]{2}[0-9]{7}"; //0000AA-0
}
