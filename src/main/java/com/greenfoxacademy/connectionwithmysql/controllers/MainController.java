package com.greenfoxacademy.connectionwithmysql.controllers;

import com.greenfoxacademy.connectionwithmysql.model.Assignee;
import com.greenfoxacademy.connectionwithmysql.model.Todo;
import com.greenfoxacademy.connectionwithmysql.service.AssigneeService;
import com.greenfoxacademy.connectionwithmysql.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
  TodoService todoService;
  AssigneeService assigneeService;

  @Autowired
  public MainController(TodoService todoService, AssigneeService assigneeService) {
    this.todoService = todoService;
    this.assigneeService = assigneeService;
  }

  @GetMapping(value = {"", "/", "/list"})
  public String listTodo(Model model, @RequestParam(required = false) Boolean isActive) {
    if (isActive == null) {
      model.addAttribute("todos", todoService.findAll());
    } else {
      model.addAttribute("todos", todoService.getActiveTodos(isActive));
    }
    return "todo-list";
  }

  @GetMapping(value = "/add")
  public String getAddNewTodo(Model model) {
    model.addAttribute("newTodo", new Todo());
    model.addAttribute("assignees",assigneeService.findAll());
    return "add-todo";
  }

  @PostMapping(value = "/add")
  public String addNewTodo(@ModelAttribute Todo todo, Long selectedAssigneeId) {
    todoService.addNewTodo(todo, selectedAssigneeId);
    return "redirect:/list";
  }

  @GetMapping(value = "/{id}/delete")
  public String deleteTodo(@PathVariable long id) {
    todoService.deleteTodoById(id);
    return "redirect:/list";
  }

  @GetMapping(value = "/{id}/edit")
  public String getEditViewTodo(@PathVariable long id, Model model) {
    model.addAttribute("selectedTodo", todoService.findById(id));
    model.addAttribute("assignees",assigneeService.findAll());
    return "edit-todo";
  }

  @PostMapping(value = "/edit")
  public String editTodo(@ModelAttribute Todo editedTodo ,Long selectedAssigneeId) {
    todoService.updateTodo(editedTodo,selectedAssigneeId);
    return "redirect:/list";
  }

  @GetMapping(value = "/{id}/details")
  public String showDetails(@PathVariable long id, Model model) {
    model.addAttribute("selectedTodo", todoService.findById(id));
    return "details-of-todo";
  }

  @GetMapping(value = "/search")
  public String searchByName(Model model, String searchInput, String searchButton) {
    model.addAttribute("searchInput", searchInput);
    model.addAttribute("todos", todoService.getSearchedTodo(searchButton, searchInput));
    return "todo-list";
  }

  @GetMapping(path = "/search-by-date")
  public String searchByDate(Model model, String date, String searchButton, String when) {
    model.addAttribute("date", date);
    model.addAttribute("todos", todoService.searchByDate(date, searchButton, when));
    return "todo-list";
  }

  @GetMapping(value = "/assignee")
  public String returnAllAssignee(Model model) {
    model.addAttribute("assignees", assigneeService.findAll());
    return "assignee-list";
  }

  @GetMapping(value = "/add-assignee")
  public String getAddNewAssignee(Model model) {
    model.addAttribute("newAssignee", new Assignee());
    return "add-assignee";
  }

  @PostMapping(value = "/add-assignee")
  public String addNewAssignee(@ModelAttribute Assignee assignee) {
    assigneeService.saveAssignee(assignee);
    return "redirect:/assignee";
  }

  @GetMapping(value = "/{id}/delete-assignee")
  public String deleteAssignee(@PathVariable long id) {
    assigneeService.deleteAssigneeById(id);
    return "redirect:/assignee";
  }

  @GetMapping(value = "/{id}/edit-assignee")
  public String getEditViewAssignee(@PathVariable long id, Model model) {
    model.addAttribute("selectedAssignee", assigneeService.findById(id));
    return "edit-assignee";
  }

  @PostMapping(value = "/edit-assignee/{id}")
  public String editAssignee(@ModelAttribute Assignee editedAssignee) {
    assigneeService.saveAssignee(editedAssignee);
    return "redirect:/assignee";
  }

  @GetMapping(value = "/{id}/details-assignee")
  public String showAssigneeDetails(@PathVariable long id, Model model) {
    model.addAttribute("selectedAssignee", assigneeService.findById(id));
    return "details-of-assignee";
  }
}