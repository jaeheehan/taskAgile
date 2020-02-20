package com.taskagile.app.infrastructure;

import com.taskagile.app.domain.common.mail.Mailer;
import com.taskagile.app.domain.common.mail.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Component
public class AsyncMailer implements Mailer {

    private static final Logger log = LoggerFactory.getLogger(AsyncMailer.class);

    /*
    private JavaMailSender mailSender;

    public AsyncMailer(JavaMailSender mailSender) {
       this.mailSender = mailSender;
    }
    */

    @Async
    @Override
    public void send(Message message) {
        Assert.notNull(message, "Parameter `message` must not be null");

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            if (!StringUtils.isEmpty(message.getFrom())) {
                mailMessage.setFrom(message.getFrom());
            }
            if (!StringUtils.isEmpty(message.getSubject())) {
                mailMessage.setSubject(message.getSubject());
            }
            if (!StringUtils.isEmpty(message.getBody())) {
                mailMessage.setText(message.getBody());
            }
            if (message.getTo() != null) {
                mailMessage.setTo(message.getTo());
            }

            log.info(mailMessage.toString());

            //mailSender.send(mailMessage);
        } catch (MailException e) {
            log.error("Failed to send mail message", e);
        }
    }
}
