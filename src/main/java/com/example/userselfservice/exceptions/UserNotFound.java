package com.example.userselfservice.exceptions;

public class UserNotFound extends RuntimeException{
    public UserNotFound(String msg){
        super(msg);
    }
}
