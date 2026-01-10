package com.crio.starter.handler;

import com.crio.starter.exception.MemeAlreadyExistsException;
import com.crio.starter.exception.NoMemeFoundException;
import com.crio.starter.exchange.ErrorResponseDto;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MemeAlreadyExistsException.class)
  public ResponseEntity<ErrorResponseDto> handleMemeExistsException(
      MemeAlreadyExistsException ex, HttpServletRequest request) {

    ErrorResponseDto error =
        new ErrorResponseDto(
            HttpStatus.CONFLICT.value(),
            HttpStatus.CONFLICT.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(NoMemeFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleNoMemeFoundException(
      NoMemeFoundException ex, HttpServletRequest request) {

    ErrorResponseDto error =
        new ErrorResponseDto(
            HttpStatus.NOT_FOUND.value(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseDto> handleValidationExceptions(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    ErrorResponseDto error =
        new ErrorResponseDto(
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(
      Exception.class) // This will handle any unspecified exception of the springboot application
  public ResponseEntity<ErrorResponseDto> handleAllUncaughtExceptions(
      Exception ex, HttpServletRequest request) {
    ErrorResponseDto error =
        new ErrorResponseDto(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error",
            "An unexpected error occurred: " + ex.getMessage(),
            request.getRequestURI());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
