package com.epam.buber.service.impl;

import com.epam.buber.controller.info.AttrValue;
import com.epam.buber.exception.ServiceException;
import com.epam.buber.service.CommonService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CommonServiceImpl implements CommonService {
    private static CommonServiceImpl commonServiceImplInstance;
    private static final String ERR_SUFFIX = "_err";
    private static final String EMPTY = "";
    private static final String WARN = "!";
    private static final String ERROR = "**";
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private CommonServiceImpl() {
    }

    public static CommonServiceImpl getInstance() {
        if (commonServiceImplInstance == null) {
            commonServiceImplInstance = new CommonServiceImpl();
        }
        return commonServiceImplInstance;
    }

    public Map<String, String> createMapFromRequest(HttpServletRequest request, String... reqParam) {
        HashMap<String, String> map = new HashMap<>();
        for (String string :
                reqParam) {
            map.put(string, request.getParameter(string));
        }
        return map;
    }

    public void setRequestValue(HttpServletRequest request, Map<String, String> map, String... reqParam) {
        for (String param :
                reqParam) {
            if (map.get(param).isEmpty()) {
                request.setAttribute(param + ERR_SUFFIX, WARN);
            } else if (map.get(param).equals(ERROR)) {
                request.setAttribute(param + ERR_SUFFIX, AttrValue.USER_EXISTS);
                map.replace(param, EMPTY);
            } else {
                request.setAttribute(param, map.get(param));
            }
        }
    }

    public String generateRandomPassword(int length) {
        return RandomStringUtils.random(length, CHARS);
    }

    public Properties readProperties(String propertyFile) throws ServiceException {
        Properties properties = new Properties();
        try (InputStream inputStream = EmailServiceImpl.class.getClassLoader().getResourceAsStream(propertyFile)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new ServiceException(e);
        }
        return properties;
    }
}
