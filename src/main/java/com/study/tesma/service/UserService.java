package com.study.tesma.service;

import com.study.tesma.entity.User;
import com.study.tesma.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);


        User returnUser = new User();

        if (user != null) {
            if (user.getPassword().equals(password)) {
                returnUser.setId(user.getId());
                returnUser.setEmail(user.getEmail());
            }
            else {
                return null;
            }
        }
        return returnUser;
    }

    public void join(String email, String password, String name) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        userRepository.save(user);
    }
}
