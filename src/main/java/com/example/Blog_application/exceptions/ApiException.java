package com.example.Blog_application.exceptions;

public class ApiException extends RuntimeException{
    public ApiException(){
        super();
    }
    public ApiException(String message){
        super(message);
    }
}
