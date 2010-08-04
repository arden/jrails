/* MailUtil.java
 * --------------------------------------
 * CREATED ON Jun 19, 2006 4:55:50 AM
 *
 * MSN arden.emily@msn.com
 * QQ 103099587（太阳里的雪）
 * MOBILE 13590309275
 *
 * ALL RIGHTS RESERVED BY ZHENUU CO,.LTD.
 * --------------------------------------
 */
package com.jrails.commons.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 邮件工具类
 * <p/>
 * Date: Jun 19, 2006 4:56:08 AM
 *
 * @author <a href="arden.emily@gmail.com">arden</a>
 */
public class MailUtils extends Object {

    private static Log logger = LogFactory.getFactory().getInstance(
            MailUtils.class);

    /**
     * This method is used to send a Message with a pre-defined mime-type.
     *
     * @param from     e-mail address of sender
     * @param to       e-mail address(es) of recipients
     * @param subject  subject of e-mail
     * @param content  the body of the e-mail
     * @param mimeType type of message, i.e. text/plain or text/html
     * @throws MessagingException the exception to indicate failure
     */
    public static void sendMessage(Session session, String from, String[] to,
                                   String[] cc, String[] bcc, String subject, String content, String mimeType)
            throws MessagingException {
        Message message = new MimeMessage(session);

        // n.b. any default from address is expected to be determined by caller.
        if (!StringUtils.isEmpty(from)) {
            InternetAddress sentFrom = new InternetAddress(from);
            message.setFrom(sentFrom);
            if (logger.isDebugEnabled()) {
                logger.debug("e-mail from: " + sentFrom);
            }
        }

        if (to != null) {
            InternetAddress[] sendTo = new InternetAddress[to.length];

            for (int i = 0; i < to.length; i++) {
                sendTo[i] = new InternetAddress(to[i]);
                if (logger.isDebugEnabled()) {
                    logger.debug("sending e-mail to: " + to[i]);
                }
            }
            message.setRecipients(Message.RecipientType.TO, sendTo);
        }

        if (cc != null) {
            InternetAddress[] copyTo = new InternetAddress[cc.length];

            for (int i = 0; i < cc.length; i++) {
                copyTo[i] = new InternetAddress(cc[i]);
                if (logger.isDebugEnabled()) {
                    logger.debug("copying e-mail to: " + cc[i]);
                }
            }
            message.setRecipients(Message.RecipientType.CC, copyTo);
        }

        if (bcc != null) {
            InternetAddress[] copyTo = new InternetAddress[bcc.length];

            for (int i = 0; i < bcc.length; i++) {
                copyTo[i] = new InternetAddress(bcc[i]);
                if (logger.isDebugEnabled()) {
                    logger.debug("blind copying e-mail to: " + bcc[i]);
                }
            }
            message.setRecipients(Message.RecipientType.BCC, copyTo);
        }
        message.setSubject((subject == null) ? "(no subject)" : subject);
        message.setContent(content, mimeType);

        // First collect all the addresses together.
        Address[] remainingAddresses = message.getAllRecipients();
        int nAddresses = remainingAddresses.length;
        boolean bFailedToSome = false;

        SendFailedException sendex = new SendFailedException(
                "Unable to send message to some recipients");

        // Try to send while there remain some potentially good addresses
        do {
            // Avoid a loop if we are stuck
            nAddresses = remainingAddresses.length;

            try {
                // Send to the list of remaining addresses, ignoring the addresses
                // attached to the message
                Transport.send(message, remainingAddresses);
            } catch (SendFailedException ex) {
                bFailedToSome = true;
                sendex.setNextException(ex);

                // Extract the remaining potentially good addresses
                remainingAddresses = ex.getValidUnsentAddresses();
            }
        }
        while (remainingAddresses != null && remainingAddresses.length > 0 && remainingAddresses.length != nAddresses);

        if (bFailedToSome) {
            throw sendex;
        }
    }

    /**
     * This method is used to send a Text Message.
     *
     * @param from    e-mail address of sender
     * @param to      e-mail addresses of recipients
     * @param subject subject of e-mail
     * @param content the body of the e-mail
     * @throws MessagingException the exception to indicate failure
     */
    public static void sendTextMessage(Session session, String from, String[] to,
                                       String[] cc, String[] bcc, String subject, String content)
            throws MessagingException {
        sendMessage(session, from, to, cc, bcc, subject, content,
                "text/plain; charset=utf-8");
    }

    /**
     * This method overrides the sendTextMessage to specify one receiver and
     * mulitple cc recipients.
     *
     * @param from    e-mail address of sender
     * @param to      e-mail addresses of recipients
     * @param subject subject of e-mail
     * @param content the body of the e-mail
     * @throws MessagingException the exception to indicate failure
     */
    public static void sendTextMessage(Session session, String from, String to,
                                       String[] cc, String[] bcc, String subject, String content)
            throws MessagingException {
        String[] recipient = null;
        if (to != null) {
            recipient = new String[]{to};
        }

        sendMessage(session, from, recipient, cc, bcc, subject, content,
                "text/plain; charset=utf-8");
    }

    /**
     * This method overrides the sendTextMessage to specify only one receiver and
     * cc recipients, rather than an array of recipients.
     *
     * @param from    e-mail address of sender
     * @param to      e-mail address of recipient
     * @param cc      e-mail address of cc recipient
     * @param subject subject of e-mail
     * @param content the body of the e-mail
     * @throws MessagingException the exception to indicate failure
     */
    public static void sendTextMessage(Session session, String from, String to,
                                       String cc, String bcc, String subject, String content)
            throws MessagingException {
        String[] recipient = null;
        String[] copy = null;
        String[] bcopy = null;

        if (to != null) {
            recipient = new String[]{to};
        }
        if (cc != null) {
            copy = new String[]{cc};
        }
        if (bcc != null) {
            bcopy = new String[]{bcc};
        }

        sendMessage(session, from, recipient, copy, bcopy, subject, content,
                "text/plain; charset=utf-8");
    }

    /**
     * This method is used to send a HTML Message
     *
     * @param from    e-mail address of sender
     * @param to      e-mail address(es) of recipients
     * @param subject subject of e-mail
     * @param content the body of the e-mail
     * @throws MessagingException the exception to indicate failure
     */
    public static void sendHTMLMessage(Session session, String from, String[] to,
                                       String[] cc, String[] bcc, String subject, String content)
            throws MessagingException {
        sendMessage(session, from, to, cc, bcc, subject, content,
                "text/html; charset=utf-8");
    }

    /**
     * This method overrides the sendHTMLMessage to specify only one sender,
     * rather than an array of senders.
     *
     * @param from    e-mail address of sender
     * @param to      e-mail address of recipients
     * @param subject subject of e-mail
     * @param content the body of the e-mail
     * @throws MessagingException the exception to indicate failure
     */
    public static void sendHTMLMessage(Session session, String from, String to,
                                       String cc, String bcc, String subject, String content)
            throws MessagingException {
        String[] recipient = null;
        String[] copy = null;
        String[] bcopy = null;

        if (to != null) {
            recipient = new String[]{to};
        }
        if (cc != null) {
            copy = new String[]{cc};
        }
        if (bcc != null) {
            bcopy = new String[]{bcc};
        }

        sendMessage(session, from, recipient, copy, bcopy, subject, content,
                "text/html; charset=utf-8");
    }

    /**
     * This method overrides the sendHTMLMessage to specify one receiver and
     * mulitple cc recipients.
     *
     * @param from    e-mail address of sender
     * @param to      e-mail address of recipient
     * @param cc      e-mail addresses of recipients
     * @param subject subject of e-mail
     * @param content the body of the e-mail
     * @throws MessagingException the exception to indicate failure
     */
    public static void sendHTMLMessage(Session session, String from, String to,
                                       String[] cc, String[] bcc, String subject, String content)
            throws MessagingException {
        String[] recipient = null;
        if (to != null) {
            recipient = new String[]{to};
        }

        sendMessage(session, from, recipient, cc, bcc, subject, content,
                "text/html; charset=utf-8");
    }

    public static void main(String... args) {
        Session session = null;
        Properties smtpProps;
        if (true) {
            Properties newSmtpProps = new Properties();
            newSmtpProps.put("mail.smtp.host", "smtp.163.com");
            // 看用户的POP3服务器是否要求用户身分论证.
            newSmtpProps.put("mail.smtp.auth", "true");
            session = Session.getInstance(newSmtpProps, new Authenticator() {

                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("smartboy_java", "avaJevoLI");
                }
            });
        } else {
            session = Session.getDefaultInstance(smtpProps);
        }
        session.setDebug(true);
    }
}
