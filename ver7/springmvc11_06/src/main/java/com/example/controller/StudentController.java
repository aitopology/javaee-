package com.example.controller;

import com.example.dao.StudentDao;
import com.example.model.Student;
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
@RequestMapping("/student")
public class StudentController {

    @Autowired //自动到Spring容器中去匹配该类型的对象，将这个对象的值赋值给该变量
    private StudentDao studentDao;
    /**
     * /student/list ==> WEB-INF/html/student/list.html
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 10;
        int offset = (page - 1) * pageSize;
        
        List<Student> students = studentDao.queryByPage(offset, pageSize);
        int total = studentDao.getTotalCount();
        int totalPages = (int) Math.ceil((double) total / pageSize);
        
        model.addAttribute("students", students);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "student/list";
    }

    @GetMapping("/addInput")
    public String addInput(Model model) {
        model.addAttribute("student", new Student());
        return "student/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Student student, BindingResult result) {
        if(studentDao.existsByNo(student.getNo())) {
            result.rejectValue("no", "error.student", "该学号已存在");
        }
        
        if(result.hasErrors()) {
            return "student/add";
        }
        
        studentDao.insert(student);
        return "redirect:/student/list";
    }

    @GetMapping("/edit/{no}")
    public String editForm(@PathVariable String no, Model model) {
        Student student = studentDao.queryByNo(no);
        model.addAttribute("student", student);
        return "student/edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Student student, @RequestParam(value = "page", defaultValue = "1") int page) {
        studentDao.updateByNo(student);
        return "redirect:/student/list?page=" + page;
    }
    @GetMapping("/delete/{no}")
    public String delete(@PathVariable String no, @RequestParam(value = "page", defaultValue = "1") int page) {
        studentDao.deleteByNo(no);
        return "redirect:/student/list?page=" + page;
    }

    /**
     * 跳转到添加学生的页面
     * @return
     */
    
}
