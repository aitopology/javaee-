package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JspController {

    /**
     * /hello ====>/WEB-INF/jsp/hello.jsp
     * @return
     */
    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("name","springMVC");
        //转发，重定向
        return "hello";
    }
}
