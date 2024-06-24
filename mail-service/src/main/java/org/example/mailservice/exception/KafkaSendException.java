package org.example.mailservice.exception;

public class KafkaSendException extends RuntimeException{
    public KafkaSendException(String message) {
        super(message);
    }
}

