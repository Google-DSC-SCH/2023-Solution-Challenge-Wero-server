package com.gdsc.wero.domain.mbti.exception;

public class MbtiNotExistException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public MbtiNotExistException(String message){
        super(message);
    }
}
