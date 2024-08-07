package com.study.tesma.service;

import com.study.tesma.entity.LoginUser;
import com.study.tesma.repository.LoginUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginUserService {
    @Autowired
    private LoginUserRepository loginUserRepository;

    public void login(String email, String ip, String now) {
        LoginUser loginUser = new LoginUser();
        loginUser.setEmail(email);
        loginUser.setIp(ip);
        loginUser.setLoginTime(now);
        loginUserRepository.save(loginUser);
    }

    public void logout(String email) {
        loginUserRepository.deleteByEmail(email);
    }

    public int count() {
        return (int) loginUserRepository.count();
    }
}
