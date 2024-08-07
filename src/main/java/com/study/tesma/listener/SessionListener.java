package com.study.tesma.listener;

import com.study.tesma.service.LoginUserService;
import com.study.tesma.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

@Component
public class SessionListener implements HttpSessionListener {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginUserService loginUserService;

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        if (se.getSession().getAttribute("sessionKey") != null) {
            String sessionKey = (String) se.getSession().getAttribute("sessionKey");
            String email = userService.findUserEmailBySessionKey(sessionKey);
            loginUserService.logout(email, sessionKey);
            se.getSession().removeAttribute("sessionKey");
            se.getSession().removeAttribute("userName");
            se.getSession().removeAttribute("userGrade");
        }
    }
}