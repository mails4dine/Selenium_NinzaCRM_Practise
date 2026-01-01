package genericUtility;

import java.io.File;
import java.util.Properties;
import java.util.Date;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class EmailUtility {

    public void sendEmailWithReports(String... reportPaths) {
        // 1. Set SMTP Properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");

        // 2. Authenticate with App Password
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                // Use your 16-digit App Password here
                return new PasswordAuthentication("mails4dine@gmail.com", "usaikanvoifbfyea");
            }
        });

        try {
            // 3. Create Message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mails4dine@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("rathibabrtc3@gmail.com,mails4dine@gmail.com"));
            message.setSubject("Automation Test Suite Reports - " + new Date());

            // 4. Create Multi-part content
            Multipart multipart = new MimeMultipart();

            // Add the email body text
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Hello Team,\n\nPlease find the attached TestNG execution reports (Emailable and Index).");
            multipart.addBodyPart(messageBodyPart);

            // 5. Loop through and attach each file
            for (String path : reportPaths) {
                File reportFile = new File(path);
                if (reportFile.exists()) {
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    attachmentPart.attachFile(reportFile);
                    multipart.addBodyPart(attachmentPart);
                } else {
                    System.out.println("Warning: File not found at " + path);
                }
            }

            message.setContent(multipart);
            
            // 6. Send Email
            Transport.send(message);
            System.out.println("Email with multiple reports sent successfully!");

        } catch (Exception e) {
            System.err.println("Error while sending email reports.");
            e.printStackTrace();
        }
    }
}