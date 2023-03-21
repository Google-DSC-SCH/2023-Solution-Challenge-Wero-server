package com.gdsc.wero.domain.board.exception;

public class BoardNotExistException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public BoardNotExistException(String message){
        super(message);
    }
}
