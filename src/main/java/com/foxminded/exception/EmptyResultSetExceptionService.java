package com.foxminded.exception;

public class EmptyResultSetExceptionService extends EmptyResultSetExceptionDao{
    public EmptyResultSetExceptionService(String message,Throwable err){
        super(message,err);
    }
}
