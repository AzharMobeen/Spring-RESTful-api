package com.az.spring.config;

import com.az.spring.entities.Student;
import com.az.spring.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomComponent implements CommandLineRunner {

    @Autowired
    private StudentService studentService;

    @Override
    public void run(String... args)  {

        List<Student> studentList = new ArrayList<>();
        Student student = new Student("Azhar","Mobeen");
        Student student2 = new Student("Adeel","Razak");
        Student student3 = new Student("Wasif","Hameed");
        Student student4 = new Student("Adil","Qamar");
        studentService.saveStudentList(studentList);

    }
}
