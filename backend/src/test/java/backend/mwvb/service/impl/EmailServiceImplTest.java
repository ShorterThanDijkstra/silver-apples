package backend.mwvb.service.impl;

import backend.mwvb.entity.Email;
import backend.mwvb.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

@SpringBootTest
class EmailServiceImplTest {
    private EmailService emailService;

    @Value("${spring.datasource.username}")
    String dbUsername;

    @Value("${spring.datasource.password}")
    String dbPassword;

    @Value("${spring.mail.password}")
    String emailPassword;

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Test
    void send() throws MessagingException {
        System.out.println(dbUsername);
        System.out.println(dbPassword);
        System.out.println(emailPassword);
        String content = """
                <html>
                <body>
                <h3>Hello! This is a welcome email!</h3>
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