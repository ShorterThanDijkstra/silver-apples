package backend.mwvb.service.impl;

import backend.mwvb.entity.User;
import backend.mwvb.exception.IllegalUserInfoException;
import backend.mwvb.mapper.UserMapper;
import backend.mwvb.service.UserService;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@Data
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final Pattern emailValidator;

    @Override
    public void register(User user) throws IllegalUserInfoException {
        validateUser(user);
        String rawPassword = user.getPassword();
        String encoded = passwordEncoder.encode(rawPassword);
        user.setPassword(encoded);
        userMapper.insert(user);
    }

    @Override
    public boolean emailExist(String email) {
        return userMapper.emailExist(email);
    }

    @Override
    public boolean usernameExist(String username) {
        return userMapper.usernameExist(username);
    }

    private boolean emptyOrNull(String str) {
        return str == null || str.trim().equals("");
    }

    private void validateUser(User user) throws IllegalUserInfoException {
        if (emptyOrNull(user.getName()) ||
                emptyOrNull(user.getPassword()) ||
                emptyOrNull(user.getNickName()) ||
                emptyOrNull(user.getEmail()) ||
                emptyOrNull(user.getConfirmPassword())) {
            throw new IllegalUserInfoException("用户信息填写不全");
        }
        if (user.getName().trim().length() < 5) {
            throw new IllegalUserInfoException("用户名长度至少为5");

        }
        if (user.getPassword().trim().length() < 10) {
            throw new IllegalUserInfoException("密码长度至少为10");

        }
        if (!emailValidator.matcher(user.getEmail()).matches()) {
            throw new IllegalUserInfoException("邮箱格式不正确");
        }
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            throw new IllegalUserInfoException("两次密码输入不一致");
        }
    }
}
