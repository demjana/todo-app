package com.greenfoxacademy.connectionwithmysql.repository;

import com.greenfoxacademy.connectionwithmysql.model.Assignee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssigneeRepository extends CrudRepository<Assignee, Long> {

}
