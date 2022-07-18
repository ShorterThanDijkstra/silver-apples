package backend.mwvb.service.impl;

import backend.mwvb.entity.AuthUser;
import backend.mwvb.entity.LoginInfo;
import backend.mwvb.exception.UserLoginException;
import backend.mwvb.mapper.UserMapper;
import backend.mwvb.service.AuthUserCacheService;
import backend.mwvb.service.UserLoginService;
import backend.mwvb.util.CommonJWTUtils;
import backend.mwvb.util.Response;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Data
public class UserLoginServiceImpl implements UserLoginService {
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final AuthUserCacheService authUserCacheService;

    @Value("${com.maerd_zinbiel.silver-apples.jwt.password}")
    private String jwtKey;

    private void validate(LoginInfo command) throws UserLoginException {
        if (command == null) {
            throw new UserLoginException("登录失败");
        }
        if (command.getEmail() == null || command.getPassword() == null) {
            throw new UserLoginException("请填写邮箱和密码");
        }
    }

    @Override
    public Response<Map<String, String>> login(LoginInfo command) throws UserLoginException {
        validate(command);
        AuthUser authUser = loginByEmail(command.getEmail(), command.getPassword());

        Integer userId = authUser.getUser().getId();
        String jwtToken = CommonJWTUtils.create(
                LOGIN_JWT_SUBJECT,
                jwtKey,
                Map.of(LOGIN_JWT_CLAIMS_KEY, String.valueOf(userId))
        );
        authUserCacheService.cacheAuthUserByUserId(authUser);
        Map<String, String> result = Map.of("token", jwtToken);
        return Response.success(result);
    }

    private AuthUser loginByEmail(String email, String password) {
        UsernamePasswordAuthenticationToken authIn = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authOut = authenticationManager.authenticate(authIn);
        return (AuthUser) authOut.getPrincipal();
    }

}
