package com.example.controller;

import com.example.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
    @Autowired
    private UserDao userDao;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        
        return "register";
    }

    @PostMapping("/register")
    public String handleRegistration(@RequestParam String username, 
                                    @RequestParam String password,
                                    Model model) {
        try {
            String encodedPassword = password;
            userDao.createUser(username, encodedPassword);
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    private boolean userExists(String username) {
        return userDao.validateUser(username, null);
    }
}