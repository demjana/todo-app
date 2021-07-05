package com.greenfoxacademy.connectionwithmysql.controllers;

import com.greenfoxacademy.connectionwithmysql.dtos.AssigneeDTO;
import com.greenfoxacademy.connectionwithmysql.dtos.ErrorDTO;
import com.greenfoxacademy.connectionwithmysql.services.AssigneeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assignee")
public class AssigneeApiController {

  private final AssigneeService assigneeService;

  @Autowired
  public AssigneeApiController(AssigneeService assigneeService) {
    this.assigneeService = assigneeService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getAssigneeById(@PathVariable Long id) {
    if (assigneeService.isAssigneeExist(id)) {
      return ResponseEntity.ok(assigneeService.getAssigneeById(id));
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ErrorDTO("No Assignee on the given index " + id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateAssigneeById(@RequestBody AssigneeDTO assigneeDTO,
                                              @PathVariable("id") Long id) {
    if (assigneeService.isAssigneeExist(id)) {
      assigneeService.updateAssignee(assigneeDTO, id);
      return ResponseEntity.ok(assigneeService.getAssigneeById(id));
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteAssigneeById(@PathVariable Long id) {
    if (assigneeService.isAssigneeExist(id)) {
      assigneeService.deleteAssigneeById(id);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ErrorDTO("No Assignee on the given index " + id));
  }
}