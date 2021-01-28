package com.foxminded.exception;

public class EmptyResultSetExceptionDao extends RuntimeException{
    public EmptyResultSetExceptionDao(String message,Throwable err){
        super(message,err);
    }
}
