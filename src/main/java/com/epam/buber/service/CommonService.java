package com.epam.buber.service;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;

public interface CommonService {
    HashMap<String, String> createMapFromRequest(HttpServletRequest request, String ... reqParam) ; //
    void setRequestValue(HttpServletRequest request, HashMap<String, String> map, String ... reqParam);
    String generateRandomPassword(int length);
}
