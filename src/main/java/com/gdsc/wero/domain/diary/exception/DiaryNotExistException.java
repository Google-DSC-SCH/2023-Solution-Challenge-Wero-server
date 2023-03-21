package com.gdsc.wero.domain.diary.exception;

public class DiaryNotExistException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    public DiaryNotExistException(String message){
        super(message);
    }
}


