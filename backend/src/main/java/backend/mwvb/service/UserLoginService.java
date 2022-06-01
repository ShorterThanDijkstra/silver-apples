package backend.mwvb.service;

import backend.mwvb.entity.LoginInfo;
import backend.mwvb.exception.UserLoginException;
import backend.mwvb.util.Response;

public interface UserLoginService {
    Response<String> login(LoginInfo loginInfo) throws UserLoginException;
}
