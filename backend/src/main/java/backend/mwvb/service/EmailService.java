package backend.mwvb.service;

import backend.mwvb.entity.Email;

import javax.mail.MessagingException;

public interface EmailService {
    void sendHtmlEmail(Email email) throws MessagingException;

    void sendRegisterCompleteEmail(String jwtToken, String emailAddr, String username) throws MessagingException;
}
