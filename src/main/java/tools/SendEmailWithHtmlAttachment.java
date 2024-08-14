package tools;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class SendEmailWithHtmlAttachment {

    public static void sendEmailWithHtmlAttachment(String recipientEmail, String subject, String htmlContent, String htmlFilePath) {
        final String username = "1120620374@qq.com";
        final String password = "legbyrctuueyhggb";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.qq.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("1120620374@qq.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail));
            message.setSubject(subject);

            // 邮件的正文部分（HTML）
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlContent, "text/html; charset=utf-8");

            // 邮件的附件部分
            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(new File(htmlFilePath));
            attachmentPart.setDataHandler(new DataHandler(fds));
            attachmentPart.setFileName("测试报告.html");

            // 将正文和附件添加到多部分消息中
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);
            multipart.addBodyPart(attachmentPart);

            // 设置邮件的内容为多部分消息
            message.setContent(multipart);

            // 发送邮件
            Transport.send(message);

            System.out.println("email sent successfully");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readFileToString(String filePath) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(filePath));
            return new String(encoded, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // 或者抛出异常，取决于你的错误处理策略
        }
    }

    public static void main(String[] args) {
        //收件人邮箱
        String recipientEmail = "m15180381485@163.com,596137586@qq.com,1120620374@qq.com";
        //邮件标题
        String subject = "加油站-Web自动化测试报告";
        // report报告路径
        String htmlFilePath = "/Users/macbook_air/Desktop/Backup/Automation/GasStation/report.html";
        //email_template邮件模板的路径
        String emailFilePath = "/Users/macbook_air/Desktop/Backup/Automation/GasStation/src/main/java/tools/email_template.html";
        String htmlContent = readFileToString(emailFilePath);
        sendEmailWithHtmlAttachment(recipientEmail, subject, htmlContent, htmlFilePath);
    }
}
