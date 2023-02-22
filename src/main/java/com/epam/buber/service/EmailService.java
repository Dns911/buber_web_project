package com.epam.buber.service;

public interface EmailService {
    void sendEmail(String toAddress, String name, EmailType type);
    enum EmailType{
        WELCOME_CLIENT("email_prop/email_patterns/welcome_client.txt"),
        WELCOME_DRIVER("email_prop/email_patterns/welcome_driver.txt"),
        RESTORE_PASSWORD("email_prop/email_patterns/restore_password.txt"),
        BANNED("email_prop/email_patterns/banned.txt"),
        UNBANNED("email_prop/email_patterns/unbanned.txt");
        private String file;
        EmailType(String file) {
            this.file = file;
        }
        public String getFile() {
            return file;
        }
    }
}
