package com.springboot.webclient_blip_api.api.exception;

public class NotificationException extends RuntimeException{
    public NotificationException(String message){
        super(message);
    }
}
