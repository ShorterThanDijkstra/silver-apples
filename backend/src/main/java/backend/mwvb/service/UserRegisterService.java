package backend.mwvb.service;

import backend.mwvb.entity.RegisterInfo;
import backend.mwvb.exception.UserRegisterException;

import javax.mail.MessagingException;
import java.util.regex.Pattern;

public interface UserRegisterService {
    Long REGISTER_INFO_EXPIRE = 60 * 60 * 24 * 1000L;

    void register(RegisterInfo info) throws UserRegisterException;

    void request(RegisterInfo info) throws UserRegisterException, MessagingException;

    boolean emailExist(String email);

    boolean usernameExist(String username);
}
