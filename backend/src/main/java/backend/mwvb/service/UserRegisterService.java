package backend.mwvb.service;

import backend.mwvb.entity.RegisterInfo;
import backend.mwvb.exception.UserRegisterException;

import javax.mail.MessagingException;

public interface UserRegisterService {
    Long REGISTER_INFO_EXPIRE = 60 * 60 * 24 * 1000L;

    String REGISTER_JWT_SUBJECT = "USER-REGISTER";
    String REGISTER_JWT_CLAIMS_KEY = "USER-EMAIL";
    void complete(RegisterInfo info) throws UserRegisterException;

    void request(String email) throws UserRegisterException, MessagingException;

    boolean emailExist(String email);

    boolean usernameExist(String username);
}
