package com.epam.buber.controller.filter;

import com.epam.buber.controller.info.SessionAttrName;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "ForwardFilter", dispatcherTypes = DispatcherType.FORWARD, urlPatterns = "/pages/*")
public class ForwardFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(SessionAttrName.FILTER_ATTR, "DispatcherType.FORWARD");
        chain.doFilter(request, response);
    }
}
