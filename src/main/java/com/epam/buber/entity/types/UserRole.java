package com.epam.buber.entity.types;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public enum UserRole {

    GUEST("guest"),
    CLIENT ("client"),
    DRIVER ("driver"),
    ADMIN("admin");

    private static Logger logger = LogManager.getLogger();
    private String role;


    UserRole(String role) {
        this.role = role;
    }

    public static UserRole define(String strRole){
        List<UserRole> list = Arrays.stream(UserRole.values()).filter(o->o.toString().equals(strRole)).toList();
        return list.get(0);
    }

    @Override
    public String toString() {
        return role;
    }
}
