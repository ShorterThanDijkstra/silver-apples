package backend.mwvb.service.impl;

import backend.mwvb.entity.RegisterInfo;
import backend.mwvb.entity.User;
import backend.mwvb.exception.UserRegisterException;
import backend.mwvb.mapper.UserMapper;
import backend.mwvb.service.EmailService;
import backend.mwvb.service.UserRegisterService;
import backend.mwvb.service.UserValidateService;
import backend.mwvb.util.CommonJWTUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Map;

import static backend.mwvb.util.CommonJWTUtils.parse;

@Service
@Data
public class UserRegisterServiceImpl implements UserRegisterService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private final UserValidateService userValidateService;
    @Value("${com.maerd_zinbiel.silver-apples.jwt.password}")
    private String jwtKey;


    @Override
    public void complete(RegisterInfo info) throws UserRegisterException {
        validateRegisterInfo(info);
        String email = parse(
                info.getToken(),
                jwtKey,
                REGISTER_JWT_SUBJECT,
                REGISTER_JWT_CLAIMS_KEY,
                UserRegisterException::new);
//        validateEmail(email);        // 别人不能修改jwt。邮箱在request()里已经验证过了。
        User user = User.build(info, email);
        encodePassword(user);
        userMapper.insert(user);
    }

    private void encodePassword(User user) {
        String raw = user.getPassword();
        String encoded = passwordEncoder.encode(raw);
        user.setPassword(encoded);
    }

    @Override
    public void request(String email) throws UserRegisterException, MessagingException {
        validateEmail(email);
        String jwtToken = CommonJWTUtils.create(
                REGISTER_JWT_SUBJECT,
                jwtKey,
                REGISTER_INFO_EXPIRE,
                Map.of(REGISTER_JWT_CLAIMS_KEY, email)
        );
        emailService.sendRegisterCompleteEmail(jwtToken, email);
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
        userValidateService.validateEmail(email);
        if (emailExist(email)) {
            throw new UserRegisterException("此邮箱已经被注册: " + email);
        }
    }

    private void validateUsername(RegisterInfo info) throws UserRegisterException {
        String username = info.getUsername();
        userValidateService.validateUsername(username);
        if (usernameExist(username)) {
            throw new UserRegisterException("此用户名已经被注册");
        }
    }

    private void validatePasswords(RegisterInfo info) throws UserRegisterException {
        String password = info.getPassword();
        String confirmedPassword = info.getConfirmedPassword();
        userValidateService.validatePasswords(password, confirmedPassword);
    }

    private void validateRegisterInfo(RegisterInfo info) throws UserRegisterException {
        if (StringUtils.isAnyEmpty(
                info.getToken(),
                info.getUsername(),
                info.getPassword(),
                info.getConfirmedPassword())) {
            throw new UserRegisterException("用户信息填写不全");
        }
        validateUsername(info);
        validatePasswords(info);
    }
}
