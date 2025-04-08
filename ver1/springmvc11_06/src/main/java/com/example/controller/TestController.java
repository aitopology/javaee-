package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/get")
    public String get(HttpServletRequest req){
        String id = req.getParameter("id");
        return "get=====>id="+id;
    }

    /**
     * 方法的参数名要与请求的url中的参数名一致
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("/get2")
    public String get(int id, String name){
        return "get=====>id="+id+",name="+name;
    }

    /**
     * 该方法的参数名一定要与表单中的name属性值保持一致
     * @param pwd
     * @param name
     * @return
     */
    @RequestMapping("/post")
    public String post(String pwd, String name){
        return "post=====>pwd="+pwd+",name="+name;
    }
}
