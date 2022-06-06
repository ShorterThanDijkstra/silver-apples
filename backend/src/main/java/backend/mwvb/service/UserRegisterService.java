package backend.mwvb.service;

import backend.mwvb.entity.RegisterInfo;
import backend.mwvb.exception.UserRegisterException;

import javax.mail.MessagingException;

public interface UserRegisterService {
    Long REGISTER_INFO_EXPIRE = 60 * 60 * 24 * 1000L;

    void complete(RegisterInfo info) throws UserRegisterException;

    void request(String email) throws UserRegisterException, MessagingException;

    boolean emailExist(String email);

    boolean usernameExist(String username);
}
