package com.gdsc.wero.global.exception.errortype;

public class SaveFailException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    public SaveFailException(String message){
        super(message);
    }
}
