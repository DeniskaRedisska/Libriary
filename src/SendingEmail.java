import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public interface SendingEmail {
    private void setProperties(Properties properties) {
        properties.setProperty("mail.transoprt.protocol", "smpt");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.user", "deniskaarediskaa@gmail.com");
    }

     default void sendEmail(String from, String to, String password, String msg) throws MessagingException {
        Properties properties = new Properties();
        setProperties(properties);
        Session mailSession = Session.getDefaultInstance(properties);
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("New book");
        message.setText(msg);

        Transport.send(message, from, password);
    }
}
