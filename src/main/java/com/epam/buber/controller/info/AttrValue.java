package com.epam.buber.controller.info;

public class AttrValue {
    private AttrValue() {
    }

    public static final String LOGIN_MSG = "Некорректный логин или пароль";
    public static final String GUEST_MSG = "Гость";
    public static final String STATUS_ERR_MSG = "У вас не активный статус,\nсвяжитесь с администратором.";
    public static final String CAR_ID_ERR_MSG_1 = "Автомодиль не найден";
    public static final String CAR_ID_ERR_MSG_2 = "Введен некорректный номер,\nв формате 1234AB-7";
    public static final String CAR_ID_ERR_MSG_3 = "Вы уже на смене";
    public static final String DRIVER_OUT = "Успешно завершено";
    public static final String DRIVER_OUT_ERR = "Завершите полученный заказ\nили вы еще не на смене";
    public static final String PREORDER_ERR_MSG = "Все водители заняты попробуйте позже или смените класс авто";
    public static final String REGISTR_MSG = "Введите одинаковые пароли или " +
            "заполните корректные параметры в пустых ячейках";

    public static final String STATUS_MSG_WAIT_ORDER = "Ожидание заказа";
    public static final String STATUS_MSG_IN_ORDER = "Работа с заказом";
    public static final String STATUS_MSG_REST = "Не на смене";
    public static final String ORDER_STATUS_0 = "в работе";
    public static final String ORDER_STATUS_1 = "завершен";
    public static final String CURRENT_ORDER_MSG_1 = "В данный момент нет текущих заказов";
    public static final String CURRENT_ORDER_MSG_2 = "Вы в данным момент не на смене ";
    public static final String CURRENT_ORDER_MSG_3 = "Вы уже завершили заказ ";
    public static final String SYS_STATUS_MSG_ACTIVE = "active";
    public static final String SYS_STATUS_MSG_APPLICANT = "applicant";
    public static final String SYS_STATUS_MSG_BANNED = "banned";
    public static final String CL_AUTO_ECONOMY = "economy";
    public static final String CL_AUTO_STANDARD = "standard";
    public static final String CL_AUTO_BUSINESS = "business";
    public static final String CL_AUTO_MINIVAN = "business";

}
