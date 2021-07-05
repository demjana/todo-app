package com.greenfoxacademy.connectionwithmysql.services;

import com.greenfoxacademy.connectionwithmysql.dtos.AssigneeDTO;
import com.greenfoxacademy.connectionwithmysql.models.Assignee;
import com.greenfoxacademy.connectionwithmysql.repositories.AssigneeRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssigneeServiceImpl implements AssigneeService {

  private final AssigneeRepository assigneeRepository;

  @Autowired
  public AssigneeServiceImpl(AssigneeRepository assigneeRepository) {
    this.assigneeRepository = assigneeRepository;
  }

  @Override
  public Iterable<Assignee> findAll() {
    return assigneeRepository.findAll();
  }

  @Override
  public void saveAssignee(Assignee assignee) {
    assigneeRepository.save(assignee);
  }

  @Override
  public void deleteAssigneeById(long id) {
    Optional<Assignee> deletedAssignee = assigneeRepository.findById(id);
    deletedAssignee.ifPresent(assigneeRepository::delete);
  }

  @Override
  public Assignee findById(long id) {
    Assignee assignee = new Assignee();
    Optional<Assignee> selectedAssignee = assigneeRepository.findById(id);
    if (selectedAssignee.isPresent()) {
      assignee = selectedAssignee.get();
    }
    return assignee;
  }

  @Override
  public boolean isAssigneeExist(Long id) {
    return assigneeRepository.findById(id).isPresent();
  }

  @Override
  public Assignee getAssigneeById(Long id) {
    return assigneeRepository.findById(id).orElse(null);
  }

  @Override
  public void updateAssignee(AssigneeDTO assigneeDTO, Long id) {
    Optional<Assignee> assignee = assigneeRepository.findById(id);
    if (assignee.isPresent()) {
      Assignee foundAssignee = assignee.get();
      foundAssignee.setName(assigneeDTO.getName());
      foundAssignee.setEmail(assigneeDTO.getEmail());
      saveAssignee(foundAssignee);
    }
  }

}