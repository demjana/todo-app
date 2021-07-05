package com.greenfoxacademy.connectionwithmysql.services;

import com.greenfoxacademy.connectionwithmysql.dtos.AssigneeDTO;
import com.greenfoxacademy.connectionwithmysql.models.Assignee;

public interface AssigneeService {
  Iterable<Assignee> findAll();

  void saveAssignee(Assignee assignee);

  void deleteAssigneeById(long id);

  Assignee findById(long id);

  boolean isAssigneeExist(Long id);

  Assignee getAssigneeById(Long id);

  void updateAssignee(AssigneeDTO assigneeDTO, Long id);
}