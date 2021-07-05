package com.greenfoxacademy.connectionwithmysql.repositories;

import com.greenfoxacademy.connectionwithmysql.models.Assignee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssigneeRepository extends CrudRepository<Assignee, Long> {
}