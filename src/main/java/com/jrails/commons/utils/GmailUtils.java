package com.jrails.commons.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 使用Gmail发送邮件
 *
 * @author Winter Lau
 */
public class GmailUtils {
    private static Log logger = LogFactory.getFactory().getInstance(GmailUtils.class);

    /**
     * 设置邮件发送属性
     *
     * @param username 邮箱名，省略@后面的邮箱域名
     * @param password 邮箱密码
     */
    private Session setProperties(final String username, final String password) {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        return session;
    }

    /**
     * 设置Message属性
     *
     * @param message
     * @param to
     * @param cc
     * @param bcc
     * @return
     */
    private Message setToAddress(Message message, String[] to, String[] cc, String[] bcc) {
        try {
            if (to != null) {
                InternetAddress[] sendTo = new InternetAddress[to.length];

                for (int i = 0; i < to.length; i++) {
                    sendTo[i] = new InternetAddress(to[i], false);
                    if (logger.isDebugEnabled()) {
                        logger.debug("sending e-mail to: " + to[i]);
                    }
                }
                message.setRecipients(Message.RecipientType.TO, sendTo);
            }

            if (cc != null) {
                InternetAddress[] copyTo = new InternetAddress[cc.length];

                for (int i = 0; i < cc.length; i++) {
                    copyTo[i] = new InternetAddress(cc[i], false);
                    if (logger.isDebugEnabled()) {
                        logger.debug("copying e-mail to: " + cc[i]);
                    }
                }
                message.setRecipients(Message.RecipientType.CC, copyTo);
            }

            if (bcc != null) {
                InternetAddress[] copyTo = new InternetAddress[bcc.length];

                for (int i = 0; i < bcc.length; i++) {
                    copyTo[i] = new InternetAddress(bcc[i], false);
                    if (logger.isDebugEnabled()) {
                        logger.debug("blind copying e-mail to: " + bcc[i]);
                    }
                }
                message.setRecipients(Message.RecipientType.BCC, copyTo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    /**
     * 发送普通文本邮件
     *
     * @param username
     * @param password
     * @param to
     * @param cc
     * @param bcc
     * @param subject
     * @param content
     * @param mimeType 数据格式（"text/html;charset=utf-8")
     */
    private void sendMessage(String username, String password, String[] to, String[] cc, String[] bcc, String subject, String content, String mimeType) {
        Session session = this.setProperties(username, password);
        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(username + "@gmail.com"));
            msg = this.setToAddress(msg, to, cc, bcc);
            msg.setSubject(subject);
            msg.setContent(content, mimeType);
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送HTML邮件
     *
     * @param username
     * @param password
     * @param to
     * @param cc
     * @param bcc
     * @param subject
     * @param content
     */
    public void sendHtmlMessage(String username, String password, String[] to, String[] cc, String[] bcc, String subject, String content) {
        String mimeType = "text/html;charset=utf-8";
        this.sendMessage(username, password, to, cc, bcc, subject, content, mimeType);
    }

    /**
     * 发送普通文本邮件
     *
     * @param username
     * @param password
     * @param to
     * @param cc
     * @param bcc
     * @param subject
     * @param content
     */
    public void sendTextMessage(String username, String password, String[] to, String[] cc, String[] bcc, String subject, String content) {
        String mimeType = "text/plain;charset=utf-8";
        this.sendMessage(username, password, to, cc, bcc, subject, content, mimeType);
    }

    public static void main(String[] args) throws AddressException, MessagingException {
//        try {
//            String username = "webooking";
//            String password = "ilovewebooking!#!$";
//            String subject = "测试邮件";
//            String[] to = {"arden.emily@gmail.com"};
//            String content = "<a href='aa.html'>欢迎光临</a>";
//            new GmailUtils().sendHtmlMessage(username, password, to, null, null, subject, content);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        System.out.println(Long.MAX_VALUE);
    }
}