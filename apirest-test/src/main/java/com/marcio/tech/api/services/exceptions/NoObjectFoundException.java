package com.marcio.tech.api.services.exceptions;


public class NoObjectFoundException extends RuntimeException{

    public NoObjectFoundException(String message){
        super(message);
    }
}
