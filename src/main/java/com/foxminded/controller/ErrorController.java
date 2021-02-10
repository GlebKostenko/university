package com.foxminded.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@RequestMapping("/exceptions")
public class ErrorController {
    @ExceptionHandler(Exception.class)
    public String myRuntimeException(Exception e, RedirectAttributes redirectAttrs){
        redirectAttrs.addFlashAttribute("error", e);
        return "redirect:/exceptions";
    }

    @GetMapping()
    public String exception(){
        return "exceptions/error-page";
    }
}

