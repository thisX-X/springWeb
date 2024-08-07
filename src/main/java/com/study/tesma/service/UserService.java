package com.study.tesma.service;

import com.study.tesma.entity.SessionKey;
import com.study.tesma.entity.User;
import com.study.tesma.repository.SessionKeyRepository;
import com.study.tesma.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionKeyRepository sessionKeyRepository;

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        User returnUser = new User();

        if (user != null) {
            if (user.getPassword().equals(password)) {
                returnUser = user;
            }
            else {
                return null;
            }
        }
        return returnUser;
    }

    public boolean join(String email, String password, String name) {
        User joinedUser = userRepository.findByEmail(email);
        if (joinedUser != null) {
            return false;
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        userRepository.save(user);
        return true;
    }

    public List<User> findAllUser() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public void update(String name, String password) {
        User user = userRepository.findByEmail(name);
        user.setName(name);
        user.setPassword(password);
        userRepository.save(user);
    }

    public void update(String name) {
        User user = userRepository.findByEmail(name);
        user.setName(name);
        userRepository.save(user);
    }

    public String findUserEmailBySessionKey(String sessionKey) {
        Optional<SessionKey> sessionkey = sessionKeyRepository.findById(sessionKey);

        SessionKey sec = sessionkey.get();
        Optional<User> user = userRepository.findById(sec.getUserId());

        return user.get().getEmail();
    }

    public User findByUserId(int userId) {
        return userRepository.findById(userId).get();
    }
}
