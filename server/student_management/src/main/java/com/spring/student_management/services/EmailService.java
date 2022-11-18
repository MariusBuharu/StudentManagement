package com.spring.student_management.services;

import com.spring.student_management.dto.EmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;


@Service
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String sendMail(EmailDto emailDto) {
        try {
            if (emailDto.getMessage().contains("\n")){
                emailDto.setMessage(emailDto.getMessage().replace("\n", "<br/>"));
            }
            final MimeMessage message = mailSender.createMimeMessage();
            final MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo("your@email.com");
            helper.setFrom(sender);
            helper.setSubject(emailDto.getSubject());
            helper.setText(emailDto.initContent(), true);
            mailSender.send(message);
            return "Successfully sent mail!";
        } catch (Exception e) {
            log.error("Unable to send mail", e);
            return "Unable to send mail!";
        }
    }


}
