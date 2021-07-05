package com.greenfoxacademy.connectionwithmysql.controllers;

import com.greenfoxacademy.connectionwithmysql.models.Todo;
import com.greenfoxacademy.connectionwithmysql.services.AssigneeServiceImpl;
import com.greenfoxacademy.connectionwithmysql.services.TodoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TodoController {
  TodoServiceImpl todoServiceImpl;
  AssigneeServiceImpl assigneeServiceImpl;

  @Autowired
  public TodoController(TodoServiceImpl todoServiceImpl, AssigneeServiceImpl assigneeServiceImpl) {
    this.todoServiceImpl = todoServiceImpl;
    this.assigneeServiceImpl = assigneeServiceImpl;
  }

  @GetMapping(value = {"", "/", "/list"})
  public String listTodo(Model model, @RequestParam(required = false) Boolean isActive) {
    if (isActive == null) {
      model.addAttribute("todos", todoServiceImpl.findAll());
    } else {
      model.addAttribute("todos", todoServiceImpl.getActiveTodos(isActive));
    }
    return "todo-list";
  }

  @PostMapping(value = "/add")
  public String addNewTodo(@ModelAttribute Todo todo, Long selectedAssigneeId) {
    todoServiceImpl.addNewTodo(todo, selectedAssigneeId);
    return "redirect:/list";
  }

  @DeleteMapping(value = "/{id}/delete")
  public String deleteTodo(@PathVariable long id) {
    if (!todoServiceImpl.isTodoExist(id)) {
      return "error";
    } else {
      todoServiceImpl.deleteTodoById(id);
      return "redirect:/list";
    }
  }

  @GetMapping(value = "/{id}/edit")
  public String getEditViewTodo(@PathVariable long id, Model model) {
    if (!todoServiceImpl.isTodoExist(id)) {
      return "error";
    } else {
      model.addAttribute("selectedTodo", todoServiceImpl.findById(id));
      model.addAttribute("assignees", assigneeServiceImpl.findAll());
      return "edit-todo";
    }
  }

  @PutMapping(value = "/edit")
  public String editTodo(@ModelAttribute Todo editedTodo, Long selectedAssigneeId) {
    todoServiceImpl.updateTodo(editedTodo, selectedAssigneeId);
    return "redirect:/list";
  }

  @GetMapping(value = "/{id}/details")
  public String showDetails(@PathVariable long id, Model model) {
    if (!todoServiceImpl.isTodoExist(id)) {
      return "error";
    } else {
      model.addAttribute("selectedTodo", todoServiceImpl.findById(id));
      return "details-of-todo";
    }
  }

  @GetMapping(value = "/search")
  public String searchByName(Model model, String searchInput, String searchButton) {
    model.addAttribute("searchInput", searchInput);
    model.addAttribute("todos", todoServiceImpl.getSearchedTodo(searchButton, searchInput));
    return "todo-list";
  }

  @GetMapping(path = "/search-by-date")
  public String searchByDate(Model model, String date, String searchButton, String when) {
    model.addAttribute("date", date);
    model.addAttribute("todos", todoServiceImpl.searchByDate(date, searchButton, when));
    return "todo-list";
  }
}