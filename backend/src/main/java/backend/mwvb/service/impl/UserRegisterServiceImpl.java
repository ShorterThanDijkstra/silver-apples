package backend.mwvb.service.impl;

import backend.mwvb.entity.RegisterInfo;
import backend.mwvb.entity.User;
import backend.mwvb.exception.UserRegisterException;
import backend.mwvb.mapper.UserMapper;
import backend.mwvb.service.EmailService;
import backend.mwvb.service.UserRegisterService;
import backend.mwvb.util.CommonJWTUtils;
import backend.mwvb.util.RegisterRedisCache;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
@Data
public class UserRegisterServiceImpl implements UserRegisterService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final RegisterRedisCache registerRedisCache;

    @Value("${com.maerd_zinbiel.silver-apples.jwt.password}")
    private String jwtKey;

    @Override
    public void register(RegisterInfo info) throws UserRegisterException {
        validateRegisterInfo(info);

        String rawPasswd = info.password();
        String encodedPasswd = passwordEncoder.encode(rawPasswd);

        User user = User.fromRegisterInfo(info);
        user.setPassword(encodedPasswd);
        userMapper.insert(user);
    }

    @Override
    public void request(RegisterInfo info) throws UserRegisterException, MessagingException {
        validateRegisterInfo(info);

        String subject = info.email();
        String jwtToken = CommonJWTUtils.create(
                subject,
                jwtKey,
                REGISTER_INFO_EXPIRE
        );

        User user = User.fromRegisterInfo(info);
        encodeUserPasswd(user);

        registerRedisCache.cacheRegisterRequestUser(
                jwtToken,
                user,
                REGISTER_INFO_EXPIRE);

        emailService.sendRegisterCompleteEmail(jwtToken, info.email(), info.username());

    }

    private void encodeUserPasswd(User user) {
        String rawPasswd = user.getPassword();
        String encodedPasswd = passwordEncoder.encode(rawPasswd);
        user.setPassword(encodedPasswd);
    }

    @Override
    public boolean emailExist(String email) {
        return userMapper.emailExist(email);
    }

    @Override
    public boolean usernameExist(String username) {
        return userMapper.usernameExist(username);
    }

    private void validateEmail(String email) throws UserRegisterException {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException e) {
            throw new UserRegisterException("邮箱格式不正确");
        }
        if (userMapper.emailExist(email)) {
            throw new UserRegisterException("此邮箱已经被注册");
        }
    }

    private void validateUsername(String username) throws UserRegisterException {
        if (username.length() < 5) {
            throw new UserRegisterException("用户名长度至少为5");
        }
        if (userMapper.usernameExist(username)) {
            throw new UserRegisterException("此用户名已经被注册");
        }
    }

    private void validatePasswords(String password, String confirmedPassword) throws UserRegisterException {
        if (password.length() < 10) {
            throw new UserRegisterException("密码长度至少为10");

        }
        if (!confirmedPassword.equals(password)) {
            throw new UserRegisterException("两次密码输入不一致");
        }
    }

    private void validateRegisterInfo(RegisterInfo info) throws UserRegisterException {
        if (StringUtils.isAnyEmpty(info.email(), info.username(), info.password(), info.confirmedPassword())) {
            throw new UserRegisterException("用户信息填写不全");
        }
        validateEmail(info.email());
        validateUsername(info.username());
        validatePasswords(info.password(), info.confirmedPassword());
    }
}
