package backend.mwvb.service;

import backend.mwvb.entity.User;
import backend.mwvb.exception.UserRegisterException;

public interface UserRegisterService {
    void register(User user) throws UserRegisterException;

    boolean emailExist(String email);

    boolean usernameExist(String username);
}
