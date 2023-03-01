package com.epam.buber.service;

public interface EmailService {
    void sendEmail(String toAddress, EmailType type, String ... text);
    enum EmailType{
        WELCOME_CLIENT_1("email_prop/email_patterns/welcome_client.txt"),
        WELCOME_DRIVER_1("email_prop/email_patterns/welcome_driver.txt"),
        ORDER_START_CLIENT_8("email_prop/email_patterns/order_start_client.txt"),
        ORDER_START_DRIVER_6("email_prop/email_patterns/order_start_driver.txt"),
        RESTORE_PASSWORD_1("email_prop/email_patterns/restore_password.txt"),
        BANNED_1("email_prop/email_patterns/banned.txt"),
        UNBANNED_1("email_prop/email_patterns/unbanned.txt");
        private String file;
        EmailType(String file) {
            this.file = file;
        }
        public String getFile() {
            return file;
        }
    }
}
