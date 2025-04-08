package com.example.controller;

import com.example.dao.TeacherDao;
import com.example.model.Teacher;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired //自动到Spring容器中去匹配该类型的对象，将这个对象的值赋值给该变量
    private TeacherDao teacherDao;
    /**
     * /student/list ==> WEB-INF/html/student/list.html
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 10;
        int offset = (page - 1) * pageSize;

        List<Teacher> teachers = teacherDao.queryByPage(offset, pageSize);
        int total = teacherDao.getTotalCount();
        int totalPages = (int) Math.ceil((double) total / pageSize);

        model.addAttribute("teachers", teachers);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "teacher/list";
    }

    @GetMapping("/addInput")
    public String addInput(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "teacher/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Teacher teacher, BindingResult result) {
        if(teacherDao.existsByName(teacher.getName())) {
            result.rejectValue("name", "error.teacher", "该姓名已存在");
        }

        if(result.hasErrors()) {
            return "teacher/add";
        }

        teacherDao.insert(teacher);
        return "redirect:/teacher/list";
    }

    @GetMapping("/edit/{name}")
    public String editForm(@PathVariable String name, Model model) {
        Teacher teacher = teacherDao.queryByName(name);
        model.addAttribute("teacher", teacher);
        return "teacher/edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Teacher teacher, @RequestParam(value = "page", defaultValue = "1") int page) {
        teacherDao.updateByName(teacher);
        return "redirect:/teacher/list?page=" + page;
    }
    @GetMapping("/delete/{name}")
    public String delete(@PathVariable String name, @RequestParam(value = "page", defaultValue = "1") int page) {
        teacherDao.deleteByName(name);
        return "redirect:/teacher/list?page=" + page;
    }

    /**
     * 跳转到添加学生的页面
     * @return
     */

}