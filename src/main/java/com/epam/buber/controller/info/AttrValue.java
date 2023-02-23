package com.epam.buber.controller.info;

public class AttrValue {
    private AttrValue() {
    }
    public static final String GUEST_MSG = "Гость";
    public static final String STATUS_ERR_MSG = "У вас не активный статус,\nсвяжитесь с администратором.";
    public static final String CAR_ID_ERR_MSG_1 = "Автомодиль не найден";
    public static final String CAR_ID_ERR_MSG_2 = "Введен некорректный номер,\nв формате 1234AB-7";

    public static final String DRIVER_OUT = "Успешно завершено";
    public static final String DRIVER_OUT_ERR = "Завершите полученный заказ";



    public static final String STATUS_MSG_WAIT_ORDER = "Ожидание заказа";
    public static final String STATUS_MSG_IN_ORDER = "Работа с заказом";
    public static final String STATUS_MSG_REST = "Не на смене";

    public static final String SYS_STATUS_MSG_ACTIVE = "active";
    public static final String SYS_STATUS_MSG_APPLICANT = "applicant";
    public static final String SYS_STATUS_MSG_BANNED = "banned";


}
