package com.az.spring.rest.controller;


import com.az.spring.entities.Student;
import com.az.spring.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @PostMapping("/students")
    public Student getAllStudents(@RequestBody Student student){
        return studentService.saveStudent(student);
    }

    @GetMapping("students/{studentId}")
    public Student getStudent(@PathVariable int studentId) {
        return studentService.getStudentById(studentId);
    }

    @PutMapping("/students")
    public Student updateStudent(@RequestBody Student student){
        return studentService.updateStudent(student);
    }

    @DeleteMapping("students/{studentId}")
    public String deleteStudent(@PathVariable int studentId){
        studentService.deleteStudentById(studentId);
        return "Student successfully removed by id - "+studentId;
    }

    /*// If we enter wrong id then it will handle exception
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exe){
        StudentErrorResponse errorResponse = new StudentErrorResponse(
                HttpStatus.NOT_FOUND.value(),exe.getMessage(),System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }


    //But if we enter string as a studentid then above exception handler will not work
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(Exception exe){
        StudentErrorResponse errorResponse = new StudentErrorResponse(
                HttpStatus.BAD_REQUEST.value(),exe.getMessage(),System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }*/

}
