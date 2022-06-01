package backend.mwvb.service;

import backend.mwvb.entity.Email;

import javax.mail.MessagingException;

public interface EmailService {
    void sendHtmlEmail(Email email) throws MessagingException;
}
