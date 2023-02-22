package com.epam.buber.entity.parameter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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
//        UserRole currentRole = GUEST;
//
//        try{
//            currentRole = UserRole.valueOf(strRole);
//        } catch (IllegalArgumentException e){
//            logger.log(Level.DEBUG, "Command name exception: {}", e.getMessage());
//            return GUEST;
//        }
        List<UserRole> list = Arrays.stream(UserRole.values()).filter(o->o.getStringRole().equals(strRole)).toList();
        return list.get(0);
    }
    public String getStringRole() {
        return role;
    }

    public static void main(String[] args) {

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserRole{");
        sb.append("                role='").append(role).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
