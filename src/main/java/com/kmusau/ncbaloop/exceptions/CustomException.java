package com.kmusau.ncbaloop.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomException extends Exception {
  private String message;
  private final HttpStatus statusCode;

  public CustomException(String message, HttpStatus httpStatus) {
    super(message);
    this.statusCode = httpStatus;
  }
}
