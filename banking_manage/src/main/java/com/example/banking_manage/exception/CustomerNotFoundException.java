package com.example.banking_manage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException (String message){
        super(message);
    }
    public CustomerNotFoundException (Long id){
        super(String.valueOf(id));
    }
}
