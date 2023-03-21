package com.gdsc.wero.global.exception.errorcode;

public enum GlobalCustomErrorCode {


    // 클라이언트 error
    IllegalArgumentException("C001"),
    MethodNotAllowedException("C002"),

    // server error,
    SaveFailException("S001"),
    UserNotFoundException("S002"),
    BoardNotExistException("S003"),
    ReplyNotExistException("S004"),
    IllegalStateException("S005");



    private String errorCode;

    GlobalCustomErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public String code(){
        return errorCode;
    }




}
