package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
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
        Employee Sam = employeeRepository.insert(new Employee(null, "Sam", 23, "Female", 1200, 1L));
        //When,Then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(Sam.getId()))
                .andExpect(jsonPath("$[0].name").value(Sam.getName()))
                .andExpect(jsonPath("$[0].age").value(Sam.getAge()))
                .andExpect(jsonPath("$[0].gender").value(Sam.getGender()))
                .andExpect(jsonPath("$[0].salary").value(Sam.getSalary()))
                .andExpect(jsonPath("$[0].companyId").value(Sam.getCompanyId()));
    }

    //get specific employee by id
    @Test
    void should_return_the_employee_when_perform_get_employee_given_a_employee_id() throws Exception {
        //Given
        Employee Sam = employeeRepository.insert(new Employee(null, "Sam", 23, "Female", 1200, 1L));
        employeeRepository.insert(new Employee(null, "Luna", 23, "Female", 1200, 1L));
        //When Then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + Sam.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(Sam.getId()))
                .andExpect(jsonPath("$.name").value(Sam.getName()))
                .andExpect(jsonPath("$.age").value(Sam.getAge()))
                .andExpect(jsonPath("$.gender").value(Sam.getGender()))
                .andExpect(jsonPath("$.salary").value(Sam.getSalary()))
                .andExpect(jsonPath("$.companyId").value(Sam.getCompanyId()));
    }

    @Test
    void should_return_status_404_not_found_when_perform_employee_given_not_existed_id() throws Exception {
        //Given
        long notExistEmployeeId = 99L;
        //When Then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + notExistEmployeeId))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_the_employees_by_given_gender_when_perform_get_employee() throws Exception {
        //Given
        Employee Sam = employeeRepository.insert(new Employee(null, "Sam", 23, "Male", 1200, 1L));
        employeeRepository.insert(new Employee(null, "Luna", 23, "Female", 1200, 1L));
        //When Then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees").param("gender", "Male"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(Sam.getId()))
                .andExpect(jsonPath("$[0].name").value(Sam.getName()))
                .andExpect(jsonPath("$[0].age").value(Sam.getAge()))
                .andExpect(jsonPath("$[0].gender").value(Sam.getGender()))
                .andExpect(jsonPath("$[0].salary").value(Sam.getSalary()))
                .andExpect(jsonPath("$[0].companyId").value(Sam.getCompanyId()));
    }

    @Test
    void should_return_the_employee_when_perform_post_employees_given_a_new_employee_with_JSON_format() throws Exception {
        //Given
        String newEmployeeJSON = "{\n" +
                "    \"name\" : \"DecemberAvenue3\",\n" +
                "    \"age\" : 23,\n" +
                "    \"gender\": \"Female\",\n" +
                "    \"salary\" : 5000,\n" +
                "    \"companyId\" : 1\n" +
                "}";
        //When
        mockMvcClient.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newEmployeeJSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(notNullValue()))
                .andExpect(jsonPath("$.name").value("DecemberAvenue3"))
                .andExpect(jsonPath("$.age").value(23))
                .andExpect(jsonPath("$.gender").value("Female"))
                .andExpect(jsonPath("$.salary").value(5000))
                .andExpect(jsonPath("$.companyId").value(1));
        //Then
    }
}
