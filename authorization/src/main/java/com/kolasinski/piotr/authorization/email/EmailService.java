package com.kolasinski.piotr.authorization.email;

import com.kolasinski.piotr.authorization.authtoken.AuthToken;
import com.kolasinski.piotr.authorization.email.exception.EmailMessagingException;
import com.kolasinski.piotr.authorization.util.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.logging.Logger;

@Service
public class EmailService {
    private final static Logger logger = Logger.getLogger(EmailService.class.getName());

    private final JavaMailSender javaMailSender;
    private final TaskExecutor taskExecutor;
    private final MessageService messageService;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, TaskExecutor taskExecutor, MessageService messageService) {
        this.javaMailSender = javaMailSender;
        this.taskExecutor = taskExecutor;
        this.messageService = messageService;
    }

    public void sendEmailWithAuthToken(String to, AuthToken authToken, String locale) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setTo(to);
            Object[] testMessageArgs = {authToken.getToken()};
            messageHelper.setSubject(messageService.getMessage("email.account.activation.subject", locale));
            messageHelper.setText(messageService.getMessage("email.account.activation.text", testMessageArgs, locale));
            sendEmail(message);
            logger.info("[Email] send email to " + to);
        } catch (MessagingException | NoSuchMessageException e) {
            e.printStackTrace();
            throw new EmailMessagingException("Cannot send email");
        }
    }

    private void sendEmail(MimeMessage message) {
        taskExecutor.execute(() -> {
            javaMailSender.send(message);
        });
    }
}
