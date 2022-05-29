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
        if (!isValidUser(user)) {
            throw new IllegalUserInfoException("用户信息填写错误");
        }
        String rawPassword = user.getPassword();
        String encoded = passwordEncoder.encode(rawPassword);
        user.setPassword(encoded);
        user.setIsActive(false);
        userMapper.insert(user);
    }

    private boolean isValidUser(User user) {
        if (user.getName() == null ||
                user.getPassword() == null ||
                user.getNickName() == null ||
                user.getEmail() == null) {
            return false;
        }
        if (!emailValidator.matcher(user.getEmail()).matches()) {
            return false;
        }
        return true;
    }
}
