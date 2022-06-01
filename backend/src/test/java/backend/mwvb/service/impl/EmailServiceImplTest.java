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
        System.out.println(((EmailServiceImpl) emailService).getMailFrom());
        String content = "<html>\n" +
                "<body>\n" +
                "<h3>hello! test Html test!</h3>\n" +
                "</body>\n" +
                "</html>";

        Email email = Email.builder()
                .to("1544928966@qq.com")
                .subject("hello")
                .content(content)
                .build();
        emailService.sendHtmlEmail(email);
    }
}