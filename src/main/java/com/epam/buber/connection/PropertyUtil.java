package com.epam.buber.connection;

import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
    public static final Properties PROPERTIES = new Properties();
    static {
        loadProperties();
    }
    private PropertyUtil() {
    }
    private static void loadProperties(){
//        var inputStream: InputStream =  PropertyUtil.class.getClassLoader().getResourceAsStream("application.properties");

    }
}
