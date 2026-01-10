package com.crio.starter.exception;

public class NoMemeFoundException extends RuntimeException {

  public NoMemeFoundException(String message) {
    super(message);
  }

  public NoMemeFoundException(String message, Throwable exception) {
    super(message, exception);
  }
}
