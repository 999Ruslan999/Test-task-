package org.example.coremicroservice.exception;

public class UserCredentialNotFoundException extends RuntimeException {

    public UserCredentialNotFoundException(String message) {
        super(message);
    }

}
