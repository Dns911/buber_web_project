package com.epam.buber.service.impl;

import com.epam.buber.service.CommonService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class CommonServiceImpl implements CommonService {
    private static CommonServiceImpl commonServiceImplInstance;

    private CommonServiceImpl() {
    }

    public static CommonServiceImpl getInstance() {
        if (commonServiceImplInstance == null) {
            commonServiceImplInstance = new CommonServiceImpl();
        }
        return commonServiceImplInstance;
    }

    @Override
    public HashMap<String, Object> createMapFromRequest(HttpServletRequest request, String... reqParam) {
        HashMap<String, Object> map = new HashMap<>();
        for (String string :
                reqParam) {
            map.put(string, request.getParameter(string));
        }
        return map;
    }

    @Override
    public void setRequestValue(HttpServletRequest request, HashMap<String, Object> map, String... reqParam) {
        for (String param :
                reqParam) {
            if (map.get(param).toString().isEmpty()) {
                request.setAttribute(param + "_err", "!");
            } else if (map.get(param).equals("**")) {
                request.setAttribute(param + "_err", "Пользователь уже существует!");
                map.replace(param, "");
//                request.setAttribute(param, "");
            } else {
                request.setAttribute(param, map.get(param));
            }
        }
    }

    public String generateRandomPassword(int length) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return RandomStringUtils.random(length, chars);
    }

    public Properties readProperties(String propertyFile) {
        Properties properties = new Properties();
        try (InputStream inputStream = EmailServiceImpl.class.getClassLoader().getResourceAsStream(propertyFile)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
        return properties;
    }

}
