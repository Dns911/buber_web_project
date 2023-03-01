package com.epam.buber.service.impl;

import com.epam.buber.service.EmailService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class EmailServiceImpl implements EmailService {
    private static Logger logger = LogManager.getLogger();
    private static EmailServiceImpl emailInstance;
    private static final String YANDEX_PROPERTIES = "email_prop/smtp_Yandex.properties";
    private static final String GMAIL_PROPERTIES = "email_prop/smtp_Gmail.properties";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String FROM = "from";

    private EmailServiceImpl() {
    }

    public static EmailServiceImpl getInstance() {
        if (emailInstance == null) {
            emailInstance = new EmailServiceImpl();
        }
        return emailInstance;
    }

    private String[] readPattern(String file) {
        File txtFile = new File(file);

        String[] stringArr = {"", ""};
        try (InputStream inputStream = EmailServiceImpl.class.getClassLoader().getResourceAsStream(file)) {
            Scanner scanner = new Scanner(inputStream);
            stringArr[0] = scanner.nextLine();
            while (scanner.hasNextLine()) {
                stringArr[1] += scanner.nextLine() + "\n";
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
        return stringArr;
    }

    @Override
    public void sendEmail(String toAddress, EmailType type, String... insertText) {
        CommonServiceImpl commonService = CommonServiceImpl.getInstance();
        Properties prop = commonService.readProperties(GMAIL_PROPERTIES);
        Session session = Session.getDefaultInstance(prop,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(prop.getProperty(USER),
                                prop.getProperty(PASSWORD));
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(prop.getProperty(FROM)));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
            String[] subWithText = readPattern(type.getFile());
            message.setSubject(subWithText[0]);
            String mainText = subWithText[1];
            int i = 0;
            for (String str :
                    insertText) {
                mainText = mainText.replaceFirst("\\[INSERT_TEXT\\]" + i, str);
                i++;
            }
            message.setText(mainText);
            Transport.send(message);
        } catch (MessagingException e) {
            logger.log(Level.WARN, "Message don't send to EMAIL: {}", e.getMessage());
        }
    }
}
