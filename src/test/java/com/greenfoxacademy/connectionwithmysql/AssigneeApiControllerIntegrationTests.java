package com.greenfoxacademy.connectionwithmysql;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class AssigneeApiControllerIntegrationTests {

  @Autowired
  MockMvc mockMvc;

  @Test
  void testGetAssigneeByIdShouldReturnWithOkStatus() throws Exception {
    String responseBodyWithId1 =
        "{\"id\":1,\"name\":\"John Doe\",\"email\":\"jhon.doe@gmail.com\",\"todo\":[]}";

    mockMvc.perform(get("/assignee/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(responseBodyWithId1));
  }

  @Test
  void testShouldNotFoundAssigneeById() throws Exception {
    mockMvc.perform(get("/assignee/9")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void testUpdateAssigneeByIdShouldReturnWithOkStatus() throws Exception {
    String updateRequestBody = "{\"name\":\"akos\",\"email\":\"akos@gmail.com\"}";

    String updatedAssignee = "{\"id\":1,\"name\":\"akos\",\"email\":\"akos@gmail.com\",\"todo\":[]}";

    mockMvc.perform(put("/assignee/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(updateRequestBody))
        .andExpect(status().isOk())
        .andExpect(content().json(updatedAssignee));
  }

  @Test
  void testUpdateAssigneeByIdShouldReturnWithNotFound() throws Exception {
    String updateRequestBody = "{\"name\":\"akos\",\"email\":\"akos@gmail.com\"}";

    mockMvc.perform(put("/assignee/9")
        .contentType(MediaType.APPLICATION_JSON)
        .content(updateRequestBody))
        .andExpect(status().isNotFound());
  }

  @Test
  void testDeleteAssigneeByIdShouldDeleteAndReturnWithNotFound() throws Exception {
    String error = "{\"error\":\"No Assignee on the given index 9\"}";

    mockMvc.perform(delete("/assignee/9")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().json(error));
  }

  @Test
  void testDeleteAssigneeByIdShouldReturnWithOkStatus() throws Exception {
    mockMvc.perform(delete("/assignee/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}