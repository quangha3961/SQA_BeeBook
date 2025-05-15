package com.devpro.selenium.utils;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;

public class EmailUtils {
    private static final String EMAIL_HOST = "imap.gmail.com";
    private static final String EMAIL_USERNAME = "quangha3962@gmail.com";
    private static final String EMAIL_PASSWORD = "wosh apta burr mqpi"; // Thay thế bằng App Password 16 ký tự
    private static String latestOrderCode = null;

    public static String getLatestOrderCode() throws MessagingException, IOException {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", EMAIL_HOST);
        properties.put("mail.imaps.port", "993");
        properties.put("mail.imaps.ssl.enable", "true");
        properties.put("mail.imaps.auth", "true");
        properties.put("mail.imaps.auth.plain.disable", "true");
        properties.put("mail.imaps.auth.xoauth2.disable", "true");
        properties.put("mail.debug", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        try {
            Store store = session.getStore("imaps");
            store.connect(EMAIL_HOST, EMAIL_USERNAME, EMAIL_PASSWORD);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Lấy 5 email mới nhất
            int totalMessages = inbox.getMessageCount();
            int start = Math.max(1, totalMessages - 4);
            Message[] messages = inbox.getMessages(start, totalMessages);

            System.out.println("Tìm kiếm trong " + messages.length + " email mới nhất...");

            // Tìm kiếm từ email mới nhất đến cũ nhất
            for (int i = messages.length - 1; i >= 0; i--) {
                Message message = messages[i];
                String subject = message.getSubject();
                System.out.println("Kiểm tra email: " + subject);

                String content = getTextFromMessage(message);
                System.out.println("Nội dung email: " + content);

                // Tìm kiếm mã đơn hàng trong tiêu đề
                Pattern titlePattern = Pattern.compile("XÁC NHẬN ĐƠN HÀNG #(\\d+)");
                Matcher titleMatcher = titlePattern.matcher(subject);
                if (titleMatcher.find()) {
                    String orderCode = titleMatcher.group(1);
                    System.out.println("Tìm thấy mã đơn hàng trong tiêu đề: " + orderCode);
                    inbox.close(false);
                    store.close();
                    latestOrderCode = orderCode;
                    return orderCode;
                }

                // Tìm kiếm mã đơn hàng trong nội dung
                Pattern contentPattern = Pattern.compile("Mã đơn hàng của bạn là #(\\d+)");
                Matcher contentMatcher = contentPattern.matcher(content);
                if (contentMatcher.find()) {
                    String orderCode = contentMatcher.group(1);
                    System.out.println("Tìm thấy mã đơn hàng trong nội dung: " + orderCode);
                    inbox.close(false);
                    store.close();
                    latestOrderCode = orderCode;
                    return orderCode;
                }
            }

            System.out.println("Không tìm thấy mã đơn hàng trong các email gần đây");
            inbox.close(false);
            store.close();
        } catch (AuthenticationFailedException e) {
            System.err.println("Lỗi xác thực Gmail. Vui lòng kiểm tra:");
            System.err.println("1. Đã bật IMAP trong cài đặt Gmail");
            System.err.println("2. Đã tạo App Password và cập nhật trong code");
            System.err.println("3. Email và App Password chính xác");
            throw e;
        }
        return null;
    }

    public static void clearLatestOrderCode() {
        latestOrderCode = null;
    }

    private static String getTextFromMessage(Message message) throws MessagingException, IOException {
        if (message.isMimeType("text/plain")) {
            return MimeUtility.decodeText(message.getContent().toString());
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            return getTextFromMimeMultipart(mimeMultipart);
        }
        return "";
    }

    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                String text = bodyPart.getContent().toString();
                result.append(MimeUtility.decodeText(text));
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }
        return result.toString();
    }
} 