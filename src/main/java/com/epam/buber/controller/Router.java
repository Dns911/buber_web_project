package com.epam.buber.controller;

public class Router {
    private String page;
    private RouterType type = RouterType.FORWARD;

    public enum RouterType {
        FORWARD, REDIRECT
    }

    public Router(String page) {
        this.page = page;
    }

    public Router(String page, RouterType type) {
        this.page = page;
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public RouterType getType() {
        return type;
    }
}
