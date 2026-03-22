package com.example.Blog_application.exceptions;

public class UnsupportedFileTypeException extends RuntimeException{

    public UnsupportedFileTypeException(String message){
        super(message);
    }
}
