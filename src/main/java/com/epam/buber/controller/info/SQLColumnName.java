package com.epam.buber.controller.info;

public class SQLColumnName {
    private SQLColumnName() {
    }
//    clients 10 columns
//    drivers 13 columns
//    drivers_shifts 8 columns
//    cars 6 columns
//    orders 12 columns

//    main column
    public static final String ID = "id";
// clients + drivers

    public static final String EMAIL = "email";
    public static final String PASS = "password";
    public static final String PHONE = "phone";
    public static final String NAME = "name";
    public static final String LAST_NAME = "last_name";
    public static final String DATE_REGISTRY = "date_registry";
    public static final String ROLE = "role";
    public static final String RATE = "rate";
 // clients
    public static final String PAYMENT_SUM = "payment_sum";
 // drivers
    public static final String DRIVER_LIC_NUMBER = "driver_lic_number";
    public static final String DRIVER_LIC_VALID = "driver_lic_valid";    //date
    public static final String STATUS = "status";
    public static final String INCOME_SUM = "income_sum";

//    driver shift
    public static final String DRIVER_ID = "driver_id";
    public static final String CAR_ID = "car_id";
    public static final String DATE = "date";
    public static final String TIME_START = "time_start";
    public static final String TIME_FINISH = "time_finish";
    public static final String INCOME = "income"; // BYN
    public static final String LENGTH_KM = "length_km";
//    car
    public static final String MODEL = "model";
    public static final String CAR_BODY = "car_body";
    public static final String YEAR_ISSUE = "year_issue";
    public static final String COLOR = "color";
    public static final String OWNER = "owner";

//    order
    public static final String CLIENT_ID = "client_id";
    public static final String DRIVER_SHIFT_ID = "driver_shift_id";
//    public static final String DATE = "date";
    public static final String START_TIME = "start_time";
    public static final String FINISH_TIME = "finish_time";
    public static final String START_POINT = "start_point";
    public static final String FINISH_POINT = "finish_point";
//   public static final String LENGTH_KM = "length_km";
//   public static final String INCOME = "income";
    public static final String RATE_CLIENT = "rate_client";
    public static final String RATE_DRIVER = "rate_driver";


}
