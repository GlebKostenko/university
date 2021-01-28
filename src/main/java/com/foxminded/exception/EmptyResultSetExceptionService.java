package com.foxminded.exception;

public class EmptyResultSetExceptionService extends RuntimeException{
    public EmptyResultSetExceptionService(String message,Throwable err){
        super(message,err);
    }
}
