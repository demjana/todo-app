package com.greenfoxacademy.connectionwithmysql;

import com.greenfoxacademy.connectionwithmysql.model.Assignee;
import com.greenfoxacademy.connectionwithmysql.model.Todo;
import com.greenfoxacademy.connectionwithmysql.repository.AssigneeRepository;
import com.greenfoxacademy.connectionwithmysql.repository.TodoRepository;
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
    todoRepository.save(new Todo("wake up at least once a day", "personal", "basic", false, true));
    todoRepository.save(new Todo("eat and drink occasionally", "personal", "easy", false, true));
    todoRepository.save(new Todo("wear clothes to work", "business", "easy", false, false));
    todoRepository.save(new Todo("email mom", "business", "hard", false, false));
    todoRepository.save(new Todo("stuff", "business", "basic", true, true));
    todoRepository.save(new Todo("be awesome", "personal", "hard", false, false));
    todoRepository.save(new Todo("go to sleep at least once a day", "personal", "basic", true, false));
    assigneeRepository.save(new Assignee("John Doe", "jhon.doe@gmail.com"));
    assigneeRepository.save(new Assignee("John Martin", "johnmartin@gmail.com"));
    assigneeRepository.save(new Assignee("Jane Doe", "jane_doe@hotmail.com"));
    assigneeRepository.save(new Assignee("Bill Waits", "bill.waits@freemail.com"));
  }
}

