package com.greenfoxacademy.connectionwithmysql.dtos;

public class ErrorDTO {

  private String error;

  public ErrorDTO(String error) {
    this.error = error;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }
}