package backend.mwvb.service.impl;

import backend.mwvb.entity.Email;
import backend.mwvb.service.EmailService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static j2html.TagCreator.*;

@Service
@Data
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Override
    public void sendHtmlEmail(Email email) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(mailFrom);
        helper.setTo(email.getTo());
        helper.setSubject(email.getSubject());
        helper.setText(email.getContent(), true);

        mailSender.send(message);
    }

    private String buildActivationUrl(String jwtToken) {
        return "http://localhost:8080/api/v1.0/user/register/" + jwtToken;
    }

    private Email buildRegisterCompleteEmail(String jwtToken, String emailAddr, String username) {
        String url = buildActivationUrl(jwtToken);
        String content = body(
                p("Hello, " + username + "!"),
                div(
                        p("To complete your registration, click the link below: "),
                        a("Confirm your account").withHref(url)
                )
        ).render();
        return Email.builder()
                .to(emailAddr)
                .subject("Complete Your Account Registration")
                .content(content)
                .build();
    }

    @Override
    @Async
    public void sendRegisterCompleteEmail(String jwtToken, String emailAddr, String username) throws MessagingException {
        Email email = buildRegisterCompleteEmail(jwtToken, emailAddr, username);
        sendHtmlEmail(email);
    }
}
