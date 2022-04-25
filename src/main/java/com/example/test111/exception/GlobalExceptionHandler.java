package com.example.test111.exception;

import com.example.test111.bean.ErrorResult;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResult> otherException(Exception ex) {
      ErrorResult errorResult = new ErrorResult(Arrays.toString(ex.getStackTrace()));
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResult);
    }
}
