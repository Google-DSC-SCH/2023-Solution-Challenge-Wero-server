package com.gdsc.wero.global.exception.errortype;

public class GcsUploadFailException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public GcsUploadFailException(String message) {
        super(message);
    }
}
