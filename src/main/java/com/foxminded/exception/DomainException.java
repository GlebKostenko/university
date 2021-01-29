package com.foxminded.exception;

public class DomainException extends RuntimeException{
    public DomainException(String message, Throwable err){
        super(message,err);
    }
}
