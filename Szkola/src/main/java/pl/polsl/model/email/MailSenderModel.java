package pl.polsl.model.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailSenderModel {

    private final Properties properties;
    private String topic;
    private String messageText;

    public MailSenderModel() {
        topic = "Topic";
        messageText = "";
        properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    private Message prepareMessage(Session session, String email, String recipient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(topic);
            message.setText(messageText);
            return message;
        } catch (Exception e) {
            Logger.getLogger(MailSenderModel.class.getName()).log(Level.SEVERE, "Could not create message", e);
        }
        return null;
    }

    public Boolean sendMail(String recipient) {
        String email = "tabulatorysemestr6@gmail.com";
        String password = "4Dup41945";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        Message message = prepareMessage(session, email, recipient);
        try {
            Transport.send(message);
            return true;
        } catch (Exception e) {
            Logger.getLogger(MailSenderModel.class.getName()).log(Level.SEVERE, "Could not send message", e);
            return false;
        }
    }
}
