package backend.mwvb.service.impl;

import backend.mwvb.entity.Email;
import backend.mwvb.service.EmailService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
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

    @Value("${com.maerd_zinbiel.silver-apples.email.register-complete-url}")
    private String registerCompleteEmailUrl;
    @Value("${com.maerd_zinbiel.silver-apples.email.send}")
    private Boolean send;
    @Value("${com.maerd_zinbiel.silver-apples.email.change-password-url}")
    private String changePasswordEmailUrl;
    public void sendHtmlEmail(Email email) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(mailFrom);
        helper.setTo(email.getTo());
        helper.setSubject(email.getSubject());
        helper.setText(email.getContent(), true);

        mailSender.send(message);
    }

    private String buildUrlWithToken(String jwtToken, String baseUrl) {
        return baseUrl + jwtToken;
    }

    private Email buildRegisterCompleteEmail(String jwtToken, String emailAddr) {
        String url = buildUrlWithToken(jwtToken, registerCompleteEmailUrl);
        String content = body(
                p("Welcome!"),
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
    public void sendRegisterCompleteEmail(String jwtToken, String emailAddr) throws MessagingException {

        if (!send) {
            return;
        }
        Email email = buildRegisterCompleteEmail(jwtToken, emailAddr);
        sendHtmlEmail(email);
    }

    @Override
    @Async
    public void sendChangePasswordEmail(String jwtToken, String emailAddr) throws MessagingException {
        if (!send) {
            return;
        }
        Email email = buildChangePasswordEmail(jwtToken, emailAddr);
        sendHtmlEmail(email);
    }

    private Email buildChangePasswordEmail(String jwtToken, String emailAddr) {
        String url = buildUrlWithToken(jwtToken, changePasswordEmailUrl);
        String content = body(
                div(
                        p("Click the link below to reset your password: "),
                        a("Reset Password").withHref(url)
                )
        ).render();
        return Email.builder()
                .to(emailAddr)
                .subject("Reset Password")
                .content(content)
                .build();
    }

}
