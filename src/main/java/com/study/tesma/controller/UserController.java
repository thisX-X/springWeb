package com.study.tesma.controller;

import com.study.tesma.entity.User;
import com.study.tesma.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public String login() {
        return "login";
    }

    @GetMapping("/join")
    public String join(Model model) {
        return "join";
    }

    @PostMapping("/user")
    public String login(Model model, HttpSession session, HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userService.login(email, password);
        if (user.getEmail() != null) {
            session.setAttribute("user", user);
        }
        return "redirect:/";
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
        session.invalidate();
        return "main";
    }
}
