package com.gdsc.wero.global.auth.jwt.exception.errortype;

public class JwtTokenExpiredException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public JwtTokenExpiredException(String message) {
        super(message);
    }
}
