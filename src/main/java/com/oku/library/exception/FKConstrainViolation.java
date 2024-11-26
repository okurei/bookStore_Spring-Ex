package com.oku.library.exception;

public class FKConstrainViolation  extends RuntimeException {
    public FKConstrainViolation(String message){
        super(message);
    }
}
