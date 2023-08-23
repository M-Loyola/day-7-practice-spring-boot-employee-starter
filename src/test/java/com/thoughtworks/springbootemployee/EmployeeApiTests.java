package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeApiTests {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MockMvc mockMvcClient;
    @BeforeEach
    void cleanupEmployeeData() {
        employeeRepository.cleanAll();
    }
    @Test
    void should_return_all_given_employees_when_perform_get_employees() throws Exception {
        //Given
        Employee Sam =  employeeRepository.insert(new Employee(null,"Sam",23,"Female",1200, 1L));
        //When,Then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(Sam.getId()))
                .andExpect(jsonPath("$[0].name").value(Sam.getName()))
                .andExpect(jsonPath("$[0].age").value(Sam.getAge()))
                .andExpect(jsonPath("$[0].gender").value(Sam.getGender()))
                .andExpect(jsonPath("$[0].salary").value(Sam.getSalary()))
                .andExpect(jsonPath("$[0].companyId").value(Sam.getCompanyId()));
    }
}
