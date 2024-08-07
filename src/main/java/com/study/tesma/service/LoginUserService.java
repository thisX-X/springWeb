package com.study.tesma.service;

import com.study.tesma.entity.LoginUser;
import com.study.tesma.entity.SessionKey;
import com.study.tesma.repository.LoginUserRepository;
import com.study.tesma.repository.SessionKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginUserService {
    @Autowired
    private LoginUserRepository loginUserRepository;

    @Autowired
    private SessionKeyRepository sessionKeyRepository;

    public void login(String email, String ip, String now, int userId) {
        LoginUser loginUser = new LoginUser();
        loginUser.setEmail(email);
        loginUser.setIp(ip);
        loginUser.setLoginTime(now);
        loginUserRepository.save(loginUser);

        SessionKey sessionKey = new SessionKey();
        sessionKey.setId(now);
        sessionKey.setUserId(userId);

        sessionKeyRepository.save(sessionKey);
    }

    public void logout(String email, String sessionKey) {
        loginUserRepository.deleteByEmail(email);
        sessionKeyRepository.deleteById(sessionKey);
    }

    public int count() {
        return (int) loginUserRepository.count();
    }
}
