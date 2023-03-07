package com.epam.buber.service;

import com.epam.buber.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public interface CommonService {
    Map<String, String> createMapFromRequest(HttpServletRequest request, String... reqParam); //

    void setRequestValue(HttpServletRequest request, Map<String, String> map, String... reqParam);

    String generateRandomPassword(int length);

    Properties readProperties(String propertyFile) throws ServiceException;
}
