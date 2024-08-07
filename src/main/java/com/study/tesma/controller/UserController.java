package com.study.tesma.controller;

import com.study.tesma.entity.LoginUser;
import com.study.tesma.entity.User;
import com.study.tesma.service.LoginUserService;
import com.study.tesma.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private LoginUserService loginUserService;

    @GetMapping("/user")
    public String login() {
        return "login";
    }

    @GetMapping("/join")
    public String join(Model model) {
        return "join";
    }

    @PostMapping("/user")
    public String login(Model model, HttpSession session, HttpServletRequest request) throws Exception {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String ip = getUserIp();
        LocalDateTime now = LocalDateTime.now();
        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));

        User user = userService.login(email, password);
        loginUserService.login(email, ip, formatedNow, user.getId());
        if (user.getEmail() != null) {
            session.setAttribute("userName", user.getName());
            session.setAttribute("userGrade", user.getGrade());
            session.setAttribute("sessionKey", formatedNow);
        }
        return "redirect:/";
    }

    @PostMapping("/change")
    public String change(Model model, HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        String name = request.getParameter("name");
        if (request.getParameter("password") != null) {
            String password = request.getParameter("password");
            userService.update(name, password);
        }
        userService.update(name);

        return "redirect:/user/detail";
    }

    @PostMapping("/join")
    public String join(Model model, HttpSession session, HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");

        Boolean userTest = userService.join(email, password, name);
        if (userTest) {
            model.addAttribute("message", "가입이 완료되었습니다.");
            return "login";
        }

        model.addAttribute("message","이미 가입된 이메일 입니다.");
        return "join";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        String sessionKey = (String) session.getAttribute("sessionKey");

        String email = userService.findUserEmailBySessionKey(sessionKey);
        loginUserService.logout(email, sessionKey);
        session.removeAttribute("sessionKey");
        return "redirect:/";
    }

    @GetMapping("/user/detail")
    public String user(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        return "user";
    }

    public String getUserIp() throws Exception {

        String ip = null;
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-RealIP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
