package ru.alfastrah.prototype;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.net.ssl.SSLSocketFactory;
import java.util.Properties;

import static javax.mail.internet.InternetAddress.parse;

class EmailSender {

    void sendEmail(String subject, String messageBody) throws MessagingException {

        String emailFrom = "AlfaHakaton@yandex.ru";

        Session session = createSession();

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emailFrom));
        message.setRecipients(Message.RecipientType.TO, parse(emailFrom));
        message.setSubject(subject);
        message.setContent(createBody(messageBody));

        Transport.send(message);
    }

    private Session createSession() {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.host", "smtp.yandex.com");
        properties.put("mail.smtp.port", 465);
        properties.put("mail.smtp.socketFactory.port", 465);
        properties.put("mail.smtp.socketFactory.class", SSLSocketFactory.class.getName());

        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("AlfaHakaton@yandex.ru", "alfapolis");
            }
        });
    }

    private static Multipart createBody(String text) throws MessagingException {

        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(text, "text/html");

        Multipart body = new MimeMultipart();
        body.addBodyPart(bodyPart);
        return body;
    }
}
