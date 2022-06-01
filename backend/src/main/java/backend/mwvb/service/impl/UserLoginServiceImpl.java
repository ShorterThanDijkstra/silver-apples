package backend.mwvb.service.impl;

import backend.mwvb.entity.AuthUser;
import backend.mwvb.entity.LoginInfo;
import backend.mwvb.exception.UserLoginException;
import backend.mwvb.mapper.UserMapper;
import backend.mwvb.service.UserLoginService;
import backend.mwvb.util.Response;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Data
public class UserLoginServiceImpl implements UserLoginService {
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    private void validate(LoginInfo command) throws UserLoginException {
        if (command == null) {
            throw new UserLoginException("登录失败");
        }
        if (command.getEmail() == null || command.getPassword() == null) {
            throw new UserLoginException("请填写邮箱和密码");
        }
    }

    @Override
    public Response<String> login(LoginInfo command) throws UserLoginException {
        validate(command);
        AuthUser authUser = loginByEmail(command.getEmail(), command.getPassword());
        return Response.success(authUser.getPassword());
    }

    private AuthUser loginByEmail(String email, String password) throws UserLoginException {
        UsernamePasswordAuthenticationToken authIn = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authOut = authenticationManager.authenticate(authIn);
        return (AuthUser) authOut.getPrincipal();
    }

}
