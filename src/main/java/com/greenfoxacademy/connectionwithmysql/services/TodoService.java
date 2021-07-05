package com.greenfoxacademy.connectionwithmysql.services;

import com.greenfoxacademy.connectionwithmysql.models.Todo;
import java.util.List;

public interface TodoService {
  Iterable<Todo> findAll();

  List<Todo> getActiveTodos(boolean isActive);

  void addNewTodo(Todo todo, Long assigneeID);

  void deleteTodoById(long id);

  Todo findById(long id);

  void updateTodo(Todo todo, Long selectedAssigneeID);

  List<Todo> getSearchedTodo(String searchButton, String searchInput);

  List<Todo> searchByDate(String date, String searchButton, String when);

  boolean isTodoExist(Long id);

  Todo getTodoById(Long id);
}
