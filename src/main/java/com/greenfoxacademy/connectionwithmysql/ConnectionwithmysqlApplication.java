package com.greenfoxacademy.connectionwithmysql;

import com.greenfoxacademy.connectionwithmysql.models.Assignee;
import com.greenfoxacademy.connectionwithmysql.models.Todo;
import com.greenfoxacademy.connectionwithmysql.repositories.AssigneeRepository;
import com.greenfoxacademy.connectionwithmysql.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConnectionwithmysqlApplication implements CommandLineRunner {

  TodoRepository todoRepository;
  AssigneeRepository assigneeRepository;

  @Autowired
  public ConnectionwithmysqlApplication(TodoRepository todoRepository, AssigneeRepository assigneeRepository) {
    this.todoRepository = todoRepository;
    this.assigneeRepository = assigneeRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(ConnectionwithmysqlApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    todoRepository.save(new Todo("Morning walk", "personal", "hard", false, true));
    todoRepository.save(new Todo("Buy milk", "personal", "easy", true, true));
    todoRepository.save(new Todo("Pay bills", "business", "basic", true, true));
    todoRepository.save(new Todo("Feed the dog", "personal", "easy", false, false));
    todoRepository.save(new Todo("Work out", "personal", "hard", false, false));
    todoRepository.save(new Todo("Business dinner", "business", "basic", true, false));
    todoRepository.save(new Todo("Read a book", "personal", "easy", false, false));
    assigneeRepository.save(new Assignee("John Doe", "jhon.doe@gmail.com"));
    assigneeRepository.save(new Assignee("Jane Doe", "jane_doe@gmail.com"));
  }
}