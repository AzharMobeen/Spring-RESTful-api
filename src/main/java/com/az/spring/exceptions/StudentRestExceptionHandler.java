package com.az.spring.exceptions;

import com.az.spring.rest.response.StudentErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StudentRestExceptionHandler {
    // If we enter wrong id then it will handle exception
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
    }
}
