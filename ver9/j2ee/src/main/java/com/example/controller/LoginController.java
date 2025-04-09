package com.example.controller;

import com.example.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @RequestMapping("/")
    public String rootRedirect() {
        return "redirect:/login";
    }

    @Autowired
    private UserDao userDao;

    @GetMapping("/login")
    public String loginForm(HttpSession session) {
        session.removeAttribute("error");
        return "/login";
    }

    @PostMapping("/login")
    public String doLogin(String username, String password, HttpSession session, Model model) {
        if(userDao.validateUser(username, password)) {
            session.setAttribute("loginUser", username);
            return "redirect:/student/list";
        }
        model.addAttribute("error", "用户名或密码错误");
        return "/login";
    }
}