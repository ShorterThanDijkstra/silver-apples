package backend.mwvb.service.impl;

import backend.mwvb.entity.AuthUser;
import backend.mwvb.entity.User;
import backend.mwvb.exception.UserLoginException;
import backend.mwvb.mapper.UserMapper;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Data
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userMapper.queryUserByEmail(email);
        try {
            validateUser(user);
        } catch (UserLoginException e) {
            throw new RuntimeException(e);
        }
        return new AuthUser(user);
    }

    private void validateUser(User user) throws UserLoginException {
        if (Objects.isNull(user)) {
            throw new UserLoginException("Bad credentials");
        }
        if (user.getId() == null ||
                user.getName() == null ||
                user.getPassword() == null ||
                user.getEmail() == null ||
                user.getCreateTime() == null) {
            throw new UserLoginException("登录失败，未知原因");
        }
    }
}
