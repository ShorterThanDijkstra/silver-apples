package backend.mwvb.service;

import backend.mwvb.entity.User;
import backend.mwvb.exception.IllegalUserInfoException;

public interface UserService {
    void register(User user) throws IllegalUserInfoException;
}
