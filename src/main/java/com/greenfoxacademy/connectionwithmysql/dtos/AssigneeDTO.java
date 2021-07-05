package com.greenfoxacademy.connectionwithmysql.dtos;

public class AssigneeDTO {

  private String name;
  private String email;

  public AssigneeDTO() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}