package com.gdsc.wero.global.exception.controlleradvice;

import com.gdsc.wero.domain.board.exception.BoardNotExistException;
import com.gdsc.wero.domain.reply.exception.ReplyNotExistException;
import com.gdsc.wero.global.exception.ErrorMessage;
import com.gdsc.wero.global.auth.jwt.exception.errorcode.AuthCustomErrorCode;
import com.gdsc.wero.global.auth.jwt.exception.errortype.RefreshTokenNotFoundException;
import com.gdsc.wero.global.auth.jwt.exception.errortype.RefreshTokenExpiredException;
import com.gdsc.wero.global.exception.errorcode.GlobalCustomErrorCode;
import com.gdsc.wero.global.exception.errortype.GcsUploadFailException;
import com.gdsc.wero.global.exception.errortype.SaveFailException;
import com.gdsc.wero.global.exception.errortype.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.Date;



@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    /**
     * RefreshTokenExpiredException : 403
     */

    @ExceptionHandler(value = RefreshTokenExpiredException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403 error
    public ErrorMessage handleTokenRefreshException(RefreshTokenExpiredException ex, WebRequest request) {

        log.error(ex.getMessage());

        return new ErrorMessage(
                AuthCustomErrorCode.RefreshTokenExpiredException.code(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(value = RefreshTokenNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 error
    public ErrorMessage handleTokenNotFoundException(RefreshTokenNotFoundException ex, WebRequest request) {

        log.error(ex.getMessage());

        return new ErrorMessage(
                AuthCustomErrorCode.RefreshTokenNotFoundException.code(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    /**
     * Client Error
     */

    @ExceptionHandler(value = MethodNotAllowedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED) // 405 error
    public ErrorMessage handleMethodNotAllowedException(MethodNotAllowedException ex, WebRequest request) {

        log.error(ex.getMessage());

        return new ErrorMessage(
                GlobalCustomErrorCode.MethodNotAllowedException.code(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 error
    public ErrorMessage handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {

        log.error(ex.getMessage());

        return new ErrorMessage(
                GlobalCustomErrorCode.IllegalArgumentException.code(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    /**
     * Server Error
     */

    @ExceptionHandler(value = SaveFailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500 error
    public ErrorMessage handleSaveFailException(SaveFailException ex, WebRequest request) {

        log.error(ex.getMessage());

        return new ErrorMessage(
                GlobalCustomErrorCode.SaveFailException.code(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500 error
    public ErrorMessage handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {

        log.error(ex.getMessage());

        return new ErrorMessage(
                GlobalCustomErrorCode.UserNotFoundException.code(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }


    @ExceptionHandler(value = BoardNotExistException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500 error
    public ErrorMessage handleBoardNotExistException(BoardNotExistException ex, WebRequest request) {

        log.error(ex.getMessage());

        return new ErrorMessage(
                GlobalCustomErrorCode.BoardNotExistException.code(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(value = ReplyNotExistException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500 error
    public ErrorMessage handleReplyNotExistException(ReplyNotExistException ex, WebRequest request) {

        log.error(ex.getMessage());

        return new ErrorMessage(
                GlobalCustomErrorCode.ReplyNotExistException.code(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(value = IllegalStateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500 error
    public ErrorMessage handleIllegalStateException(IllegalStateException ex, WebRequest request) {

        log.error(ex.getMessage());

        return new ErrorMessage(
                GlobalCustomErrorCode.IllegalStateException.code(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(value = GcsUploadFailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500 error
    public ErrorMessage handleIllegalStateException(GcsUploadFailException ex, WebRequest request) {

        log.error(ex.getMessage());

        return new ErrorMessage(
                GlobalCustomErrorCode.GcsUploadFailException.code(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }




}

