package org.example.tools;


import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;


public class ReportMailer { private static final String SMTP_SERVER = "smtp.gmail.com";
    private static final String USERNAME = "l.ofwarhd@gmail.com";
    private static final String PASSWORD = "ngcg kbip ajpd pssr";
    private static final String FROM_EMAIL = "l.ofwarhd@gmail.com";
    private static final String TO_EMAIL = "l.ofwarhd1@gmail.com";
    private static final List<String> CC=List.of("l.ofwar@ymail.com");
    private static final String SUBJECT = "Automation Testing Report";
    static String time =DateTime.now().toString("dd/MM/yyy HH:mm a");
    private static final String BODY = "Please find the attached File of the run \nEnded on : "+time;
    private static final String REPORT_DIRECTORY = "target/cucumber-reports";
    static String zipFileName = "Cucumber_Reports.zip";

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", SMTP_SERVER);
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
       //     zipDirectory(REPORT_DIRECTORY,zipFileName);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(TO_EMAIL));
            for (String r :CC) {
                message.addRecipients(Message.RecipientType.CC,InternetAddress.parse(r));
            }


            message.setSubject(SUBJECT);

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(BODY);

            Multipart multipart =  multiPartAttachments();
            multipart.addBodyPart(textPart);
            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Email sent successfully.");
        } catch (MessagingException | IOException e) {
            LoggerFactory.getLogger(ReportMailer.class).error("Mail sending failed"+e.getMessage());
        }
    }

//    private static void zipDirectory(String sourceDirPath, String zipFilePath) throws IOException {
//        Path zipPath;
//        try {
//
//             zipPath = Files.createFile(Paths.get(zipFilePath));
//        }catch (FileAlreadyExistsException e){
//         File file =new File(zipFilePath);
//         file.delete();
//             zipPath = Files.createFile(Paths.get(zipFilePath));
//        }
//        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(zipPath))) {
//            Path sourcePath = Paths.get(sourceDirPath);
//            Files.walk(sourcePath).filter(path -> !Files.isDirectory(path)).forEach(path -> {
//                ZipEntry zipEntry = new ZipEntry(sourcePath.relativize(path).toString());
//                try {
//                    zs.putNextEntry(zipEntry);
//                    Files.copy(path, zs);
//                    zs.closeEntry();
//                } catch (IOException e) {
//                    System.err.println(e);
//                }
//            });
//        }
//
//    }

    static Multipart multiPartAttachments() throws MessagingException, IOException {
        File dir = new File(ReportMailer.REPORT_DIRECTORY);
        Multipart multipart = new MimeMultipart();
        if (dir.isDirectory()) {
            File[] files = dir.listFiles((d, name) -> name.endsWith(".html"));
            if (files != null) {
                for (File file : files) {
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    attachmentPart.attachFile(file);
                    multipart.addBodyPart(attachmentPart);
                }
            }
        } else {
            System.err.println("Provided path is not a directory.");
        }
        return multipart;
    }
}
