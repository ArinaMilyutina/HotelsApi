package com.example.hotelsapi.exception;

public class HotelAlreadyExistsException extends RuntimeException{
    public HotelAlreadyExistsException(String message){
        super(message);
    }
}
