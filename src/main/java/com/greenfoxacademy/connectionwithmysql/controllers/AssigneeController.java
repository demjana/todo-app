package com.greenfoxacademy.connectionwithmysql.controllers;

import com.greenfoxacademy.connectionwithmysql.models.Assignee;
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

@Controller
public class AssigneeController {
  TodoServiceImpl todoServiceImpl;
  AssigneeServiceImpl assigneeServiceImpl;

  @Autowired
  public AssigneeController(TodoServiceImpl todoServiceImpl, AssigneeServiceImpl assigneeServiceImpl) {
    this.todoServiceImpl = todoServiceImpl;
    this.assigneeServiceImpl = assigneeServiceImpl;
  }

  @GetMapping(value = "/add")
  public String getAddNewTodo(Model model) {
    model.addAttribute("newTodo", new Todo());
    model.addAttribute("assignees", assigneeServiceImpl.findAll());
    return "add-todo";
  }

  @GetMapping(value = "/assignee")
  public String returnAllAssignee(Model model) {
    model.addAttribute("assignees", assigneeServiceImpl.findAll());
    return "assignee-list";
  }

  @GetMapping(value = "/add-assignee")
  public String getAddNewAssignee(Model model) {
    model.addAttribute("newAssignee", new Assignee());
    return "add-assignee";
  }

  @PostMapping(value = "/add-assignee")
  public String addNewAssignee(@ModelAttribute Assignee assignee) {
    assigneeServiceImpl.saveAssignee(assignee);
    return "redirect:/assignee";
  }

  @DeleteMapping(value = "/{id}/delete-assignee")
  public String deleteAssignee(@PathVariable long id) {
    if (!assigneeServiceImpl.isAssigneeExist(id)) {
      return "error";
    } else {
      assigneeServiceImpl.deleteAssigneeById(id);
      return "redirect:/assignee";
    }
  }

  @GetMapping(value = "/{id}/edit-assignee")
  public String getEditViewAssignee(@PathVariable long id, Model model) {
    if (!assigneeServiceImpl.isAssigneeExist(id)) {
      return "error";
    } else {
      model.addAttribute("selectedAssignee", assigneeServiceImpl.findById(id));
      return "edit-assignee";
    }
  }

  @PutMapping(value = "/edit-assignee/{id}")
  public String editAssignee(@ModelAttribute Assignee editedAssignee) {
    assigneeServiceImpl.saveAssignee(editedAssignee);
    return "redirect:/assignee";

  }

  @GetMapping(value = "/{id}/details-assignee")
  public String showAssigneeDetails(@PathVariable long id, Model model) {
    if (!assigneeServiceImpl.isAssigneeExist(id)) {
      return "error";
    } else {
      model.addAttribute("selectedAssignee", assigneeServiceImpl.findById(id));
      return "details-of-assignee";
    }
  }
}