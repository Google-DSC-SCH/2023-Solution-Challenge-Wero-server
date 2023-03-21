package com.gdsc.wero.domain.reply.exception;

public class ReplyNotExistException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public ReplyNotExistException(String message) {
        super(message);
    }
}
