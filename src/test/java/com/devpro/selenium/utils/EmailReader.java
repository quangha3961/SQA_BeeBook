package com.devpro.selenium.utils;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailReader {
    private static final String EMAIL_HOST = "imap.gmail.com";
    private static final String EMAIL_USERNAME = "quangha3962@gmail.com";
    private static final String EMAIL_PASSWORD = "wosh apta burr mqpi";

    public static String getLatestOrderCode() {
        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.imaps.host", EMAIL_HOST);
            properties.put("mail.imaps.port", "993");
            properties.put("mail.imaps.ssl.enable", "true");

            Session session = Session.getInstance(properties);
            Store store = session.getStore("imaps");
            store.connect(EMAIL_HOST, EMAIL_USERNAME, EMAIL_PASSWORD);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Lấy email mới nhất
            int totalMessages = inbox.getMessageCount();
            if (totalMessages > 0) {
                Message latestMessage = inbox.getMessage(totalMessages);
                String content = getTextFromMessage(latestMessage);
                
                // Tìm mã đơn hàng trong nội dung email
                Pattern pattern = Pattern.compile("#(\\d+)");
                Matcher matcher = pattern.matcher(content);
                if (matcher.find()) {
                    return matcher.group(1);
                }
            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getTextFromMessage(Message message) throws Exception {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            return getTextFromMimeMultipart(mimeMultipart);
        }
        return "";
    }

    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < mimeMultipart.getCount(); i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result.append(bodyPart.getContent());
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result.append(html);
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }
        return result.toString();
    }
} 