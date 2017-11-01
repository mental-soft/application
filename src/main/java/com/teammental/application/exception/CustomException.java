package com.teammental.application.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception'ların yönetildiği sınıftır.
 */

public class CustomException extends Exception {

  private HttpStatus code;
  private String label;

  public CustomException(HttpStatus code, String label) {
    this.code = code;
    this.label = label;
  }

  public HttpStatus getCode() {
    return code;
  }

  public void setCode(HttpStatus code) {
    this.code = code;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

}
