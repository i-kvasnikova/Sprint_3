package ru.praktikum_services.qa_scooter.paths;

public class ApiPaths {
    public static final String COURIER_PATH = "/api/v1/courier";
    public static final String COURIER_LOGIN_PATH = COURIER_PATH + "/login";
    public static final String COURIER_DELETE_PATH = COURIER_PATH + "/%s";
    public static final String ORDERS_PATH = "/api/v1/orders";
    public static final String ORDER_GET_BY_TRACK_PATH = ORDERS_PATH + "/track?t=%s";
    public static final String ORDER_CANCEL_PATH = ORDERS_PATH + "/cancel";
}
