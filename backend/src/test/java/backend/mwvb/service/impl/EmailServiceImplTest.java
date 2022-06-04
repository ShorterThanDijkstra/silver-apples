package backend.mwvb.service.impl;

import backend.mwvb.entity.Email;
import backend.mwvb.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

@SpringBootTest
class EmailServiceImplTest {
    private EmailService emailService;

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Test
    void send() throws MessagingException {
        String content = """
                <html>
                <body>
                <p>Hello!</p>
                <p>To complete your registration, click the link below:
                </p>
                </body>
                </html>
                """;

        Email email = Email.builder()
                .to("1544928966@qq.com")
                .subject("hello")
                .content(content)
                .build();
        emailService.sendHtmlEmail(email);
    }
}