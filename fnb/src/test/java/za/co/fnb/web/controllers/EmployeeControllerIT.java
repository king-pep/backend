package za.co.fnb.web.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import za.co.fnb.common.AbstractIntegrationTest;
import za.co.fnb.entities.Employee;
import za.co.fnb.repositories.EmployeeRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

class EmployeeControllerIT extends AbstractIntegrationTest {

    @Autowired private EmployeeRepository employeeRepository;

    private List<Employee> employeeList = null;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();

        employeeList = new ArrayList<>();
        employeeList.add(new Employee(1L, "First Employee"));
        employeeList.add(new Employee(2L, "Second Employee"));
        employeeList.add(new Employee(3L, "Third Employee"));
        employeeList = employeeRepository.saveAll(employeeList);
    }

    @Test
    void shouldFetchAllEmployees() throws Exception {
        this.mockMvc
                .perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(employeeList.size())));
    }

    @Test
    void shouldFindEmployeeById() throws Exception {
        Employee employee = employeeList.get(0);
        Long employeeId = employee.getId();

        this.mockMvc
                .perform(get("/api/employees/{id}", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is(employee.getText())));
    }

    @Test
    void shouldCreateNewEmployee() throws Exception {
        Employee employee = new Employee(null, "New Employee");
        this.mockMvc
                .perform(
                        post("/api/employees")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text", is(employee.getText())));
    }

    @Test
    void shouldReturn400WhenCreateNewEmployeeWithoutText() throws Exception {
        Employee employee = new Employee(null, null);

        this.mockMvc
                .perform(
                        post("/api/employees")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Content-Type", is("application/problem+json")))
                .andExpect(
                        jsonPath(
                                "$.type",
                                is("https://zalando.github.io/problem/constraint-violation")))
                .andExpect(jsonPath("$.title", is("Constraint Violation")))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.violations", hasSize(1)))
                .andExpect(jsonPath("$.violations[0].field", is("text")))
                .andExpect(jsonPath("$.violations[0].message", is("Text cannot be empty")))
                .andReturn();
    }

    @Test
    void shouldUpdateEmployee() throws Exception {
        Employee employee = employeeList.get(0);
        employee.setText("Updated Employee");

        this.mockMvc
                .perform(
                        put("/api/employees/{id}", employee.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is(employee.getText())));
    }

    @Test
    void shouldDeleteEmployee() throws Exception {
        Employee employee = employeeList.get(0);

        this.mockMvc
                .perform(delete("/api/employees/{id}", employee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is(employee.getText())));
    }
}
