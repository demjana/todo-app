package com.greenfoxacademy.connectionwithmysql.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Assignee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String name;
  private String email;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL ,mappedBy = "assignee")
  private List<Todo> todo;

  public Assignee() {
    this.todo = new ArrayList<>();
  }

  public Assignee(String name, String email) {
    this.name = name;
    this.email = email;
    this.todo = new ArrayList<>();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public List<Todo> getTodo() {
    return todo;
  }

  public void setTodo(List<Todo> todo) {
    this.todo = todo;
  }
}
