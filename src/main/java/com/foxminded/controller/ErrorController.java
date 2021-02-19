package com.foxminded.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.validation.BindException;
import org.springframework.transaction.TransactionSystemException;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
@RequestMapping("/exceptions")
public class ErrorController {
    @ExceptionHandler(NoHandlerFoundException.class)
    public String myRuntimeException(NoHandlerFoundException e, RedirectAttributes redirectAttrs){
        redirectAttrs.addFlashAttribute("error", e);
        return "redirect:/exceptions";
    }

    @ExceptionHandler({BindException.class,
            MissingServletRequestParameterException.class,
            ConstraintViolationException.class,
            MethodArgumentTypeMismatchException.class,
            TransactionSystemException.class})
    public String myValidRuntimeException(Exception e, RedirectAttributes redirectAttrs){
        redirectAttrs.addFlashAttribute("error", e);
        return "redirect:/exceptions/validation";
    }

    @GetMapping()
    public String exception(){
        return "exceptions/error-page";
    }
    @GetMapping("/validation")
    public String valid(){
        return "exceptions/validation/valid";
    }

}

