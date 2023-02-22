package com.epam.buber.service.impl;

import com.epam.buber.service.CommonService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;


import java.util.HashMap;

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
    public HashMap<String, String> createMapFromRequest(HttpServletRequest request, String... reqParam) {
        HashMap<String, String> map = new HashMap<>();
        for (String string:
             reqParam) {
            map.put(string, request.getParameter(string));
        }
        return map;
    }

    @Override
    public void setRequestValue(HttpServletRequest request, HashMap<String, String> map, String... reqParam) {
        for (String param:
             reqParam) {
            if(map.get(param).isEmpty()){
                request.setAttribute(param + "_err", "!");
            }
            else if (map.get(param).equals("**")) {
                request.setAttribute(param + "_err", "Пользователь уже существует!");
                map.replace(param, "");
//                request.setAttribute(param, "");
            }
            else {
            request.setAttribute(param, map.get(param));
            }
        }
    }

    public String generateRandomPassword(int length){
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return RandomStringUtils.random(length, chars);
    }

}
