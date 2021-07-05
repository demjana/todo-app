package com.greenfoxacademy.connectionwithmysql.services;

import com.greenfoxacademy.connectionwithmysql.models.Assignee;
import com.greenfoxacademy.connectionwithmysql.models.Todo;
import com.greenfoxacademy.connectionwithmysql.repositories.TodoRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.joda.time.DateTimeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {

  private final TodoRepository todoRepository;
  private final AssigneeServiceImpl assigneeServiceImpl;

  @Autowired
  public TodoServiceImpl(TodoRepository todoRepository, AssigneeServiceImpl assigneeServiceImpl) {
    this.todoRepository = todoRepository;
    this.assigneeServiceImpl = assigneeServiceImpl;
  }

  @Override
  public Iterable<Todo> findAll() {
    return todoRepository.findAll();
  }

  public List<Todo> getActiveTodos(boolean isActive) {
    List<Todo> listOfTodos = new ArrayList<>();
    todoRepository.findAll().forEach(listOfTodos::add);
    if (isActive) {
      return listOfTodos.stream().filter(todo -> !todo.isDone()).collect(Collectors.toList());
    } else {
      return listOfTodos.stream().filter(Todo::isDone).collect(Collectors.toList());
    }
  }

  @Override
  public void addNewTodo(Todo todo, Long assigneeID) {
    Assignee assignee = assigneeServiceImpl.findById(assigneeID);
    todo.setAssignee(assignee);
    List<Todo> assigneeTodos = new ArrayList<>();
    assigneeTodos.add(todo);
    assignee.setTodo(assigneeTodos);
    todoRepository.save(todo);
  }

  @Override
  public void deleteTodoById(long id) {
    Optional<Todo> deletedTodo = todoRepository.findById(id);
    deletedTodo.ifPresent(todoRepository::delete);
  }

  @Override
  public Todo findById(long id) {
    Todo todo = new Todo();
    Optional<Todo> selectedTodo = todoRepository.findById(id);
    if (selectedTodo.isPresent()) {
      todo = selectedTodo.get();
    }
    return todo;
  }

  @Override
  public void updateTodo(Todo todo, Long selectedAssigneeID) {
    Assignee assignee = assigneeServiceImpl.findById(selectedAssigneeID);
    todo.setAssignee(assignee);
    todoRepository.findById(todo.getId()).ifPresent(date -> todo.setDateOfCreation(date.getDateOfCreation()));
    List<Todo> assigneeTodos = new ArrayList<>();
    assigneeTodos.add(todo);
    assignee.setTodo(assigneeTodos);
    assigneeServiceImpl.saveAssignee(assignee);
    todoRepository.save(todo);
  }

  @Override
  public List<Todo> getSearchedTodo(String searchButton, String searchInput) {
    switch (searchButton) {
      case "search-by-title":
        return todoRepository.findAllByTitleContainsIgnoreCase(searchInput);
      case "search-by-content":
        return todoRepository.findAllByContentContainsIgnoreCase(searchInput);
      case "search-by-description":
        return todoRepository.findAllByDescriptionContainsIgnoreCase(searchInput);
      case "search-by-assignee":
        return todoRepository.findByNames(searchInput);
      case "show-all":
        return (List<Todo>) todoRepository.findAll();
    }
    return null;
  }

  @Override
  public List<Todo> searchByDate(String date, String searchButton, String when) {
    DateTimeComparator dateTimeComparator = DateTimeComparator.getDateOnlyInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    List<Todo> todos = (List<Todo>) todoRepository.findAll();
    try {
      Date convertedDate = formatter.parse(date);
      if (searchButton.equalsIgnoreCase("date-of-creation")) {
        if (when.equalsIgnoreCase("before")) {
          todos = todos.stream().filter(t -> dateTimeComparator
              .compare(convertedDate, t.getDateOfCreation()) > 0).collect(Collectors.toList()); // c > t.g() == 1
        } else if (when.equalsIgnoreCase("on-that-day")) {
          todos = todos.stream().filter(t -> dateTimeComparator
              .compare(convertedDate, t.getDateOfCreation()) == 0).collect(Collectors.toList());
        } else {
          todos = todos.stream().filter(t -> dateTimeComparator
              .compare(convertedDate, t.getDateOfCreation()) < 0).collect(Collectors.toList()); // c > t.g() == -1
        }
      } else {  // The button's value equals "due-date"
        todos = todos.stream().filter(t -> t.getDueDate() != null).collect(Collectors.toList());
        if (when.equalsIgnoreCase("before")) {
          todos = todos.stream().filter(t -> dateTimeComparator
              .compare(convertedDate, t.getDueDate()) > 0).collect(Collectors.toList()); // c > t.g() == 1
        } else if (when.equalsIgnoreCase("on-that-day")) {
          todos = todos.stream().filter(t -> dateTimeComparator
              .compare(convertedDate, t.getDueDate()) == 0).collect(Collectors.toList());
        } else {
          todos = todos.stream().filter(t -> dateTimeComparator
              .compare(convertedDate, t.getDueDate()) < 0).collect(Collectors.toList()); // c > t.g() == -1
        }
      }
    } catch (ParseException e) {
      System.out.println("Could not convert string to date!");
      System.exit(0);
    }
    return todos;
  }

  @Override
  public boolean isTodoExist(Long id) {
    return todoRepository.findById(id).isPresent();
  }

  @Override
  public Todo getTodoById(Long id) {
    return todoRepository.findById(id).orElse(null);
  }
}