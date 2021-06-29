package com.greenfoxacademy.connectionwithmysql.service;

import com.greenfoxacademy.connectionwithmysql.model.Assignee;
import com.greenfoxacademy.connectionwithmysql.repository.AssigneeRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssigneeService {
  AssigneeRepository assigneeRepository;

  @Autowired
  public AssigneeService(AssigneeRepository assigneeRepository) {
    this.assigneeRepository = assigneeRepository;
  }

  public Iterable<Assignee> findAll() {
    return assigneeRepository.findAll();
  }

  public void saveAssignee(Assignee assignee) {
    assigneeRepository.save(assignee);
  }

  public void deleteAssigneeById(long id) {
    Optional<Assignee> deletedAssignee = assigneeRepository.findById(id);
    if (deletedAssignee.isPresent()) {
      assigneeRepository.delete(deletedAssignee.get());
    }
  }

  public Assignee findById(long id) {
    Assignee assignee = new Assignee();
    Optional<Assignee> selectedAssignee = assigneeRepository.findById(id);
    if (selectedAssignee.isPresent()) {
      assignee = selectedAssignee.get();
    }
    return assignee;
  }
}
