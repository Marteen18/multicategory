package com.vyacheslavgoryunov.multicategory.controller;

import com.vyacheslavgoryunov.multicategory.common.validation.ValidationErrorResponse;
import com.vyacheslavgoryunov.multicategory.common.validation.Violation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@Controller
@ControllerAdvice
public class ErrorsController {

    @ExceptionHandler({NoHandlerFoundException.class, EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound() {
        return "errors/404";
    }

//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    ValidationErrorResponse constraintValidationException(ConstraintViolationException e, HttpServletRequest request) {
//        ValidationErrorResponse error = new ValidationErrorResponse();
//        for (ConstraintViolation violation : e.getConstraintViolations()) {
//            error.getViolations().add(
//                    new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
//        }
//
//        request.getSession().
//
//        return error;
//    }
}
