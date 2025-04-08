package com.example.test;

import com.example.dao.StudentDao;
import com.example.model.Student;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;


public class AppTest {

    @Test
    public void method() throws SQLException {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring.xml");
       /* DataSource dataSource = context.getBean("dataSource", DataSource.class);
        System.out.println(dataSource.getConnection());*/
        StudentDao studentDao = context.getBean("studentDao", StudentDao.class);
        Student student = new Student("1112001", "小白", 20);
        int i = studentDao.insert(student);
        System.out.println(i);
    }
}
