package com.greaterhill.framework.exception;

import com.greaterhill.framework.model.CommonResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<CommonResponseObject> handleInternalException(InternalException exception) {
        CommonResponseObject commonResponseObject = CommonResponseObject.builder().status("failed").message(exception.getMessage()).build();
        return ResponseEntity.badRequest().body(commonResponseObject);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonResponseObject> handleAccessDeniedException(AccessDeniedException exception) {
        CommonResponseObject commonResponseObject = CommonResponseObject.builder().status("failed").message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(commonResponseObject);
    }
}