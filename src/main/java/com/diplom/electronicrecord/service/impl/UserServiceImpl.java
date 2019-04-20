package com.diplom.electronicrecord.service.impl;

import com.diplom.electronicrecord.model.User;
import com.diplom.electronicrecord.repository.UserRepository;
import com.diplom.electronicrecord.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @Override
    public User authenticate(String login, String password) {

        if (!checkPasswordAndLoginIsNull(login,password)){
            User user = userRepository.findByLoginAndPassword(login, password);
            if (user != null) {
                return user;
            } else {
                throw new NullPointerException();
            }
        }else {
            throw new NullPointerException();
        }
    }
    private boolean checkPasswordAndLoginIsNull(String login, String password) {
       return login.trim().isEmpty() || password.trim().isEmpty();
    }


}
