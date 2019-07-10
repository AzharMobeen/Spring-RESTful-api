package com.az.spring.service;

import com.az.spring.entities.Student;
import com.az.spring.exceptions.StudentNotFoundException;
import com.az.spring.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    private List<Student> studentList = null;

    @PostConstruct
    private void init(){
        studentList = new ArrayList<>();
        Student student = new Student("Azhar","Mobeen");
        Student student2 = new Student("Adeel","Razak");
        Student student3 = new Student("Wasif","Hameed");
        Student student4 = new Student("Adil","Qamar");
        studentList.addAll(Arrays.asList(student,student2,student3,student4));
        this.saveStudentList(studentList);
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student getStudentById(int studentId){
        return studentRepository.findById(studentId).orElseThrow(
                ()-> new StudentNotFoundException("Student id not found - "+studentId));
    }

    public Student updateStudent(Student student){
        Student temp = getStudentById(student.getStudentId());
        if (temp!=null){
            return studentRepository.save(student);
        }else
            throw new StudentNotFoundException(("Student id not found - "+student.getStudentId()));
    }

    public Student saveStudent(Student student){
        return studentRepository.save(student);
    }
    public void deleteStudentById(int studentId){
        Student temp = getStudentById(studentId);
        if (temp!=null) {
            studentRepository.deleteById(studentId);
        }else
            throw new StudentNotFoundException(("Student id not found - "+studentId));
    }

    public List<Student> saveStudentList(List<Student> studentList){
        return studentRepository.saveAll(studentList);
    }
}
