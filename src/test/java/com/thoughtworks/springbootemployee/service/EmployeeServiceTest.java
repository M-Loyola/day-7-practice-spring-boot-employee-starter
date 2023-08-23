package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {
    private EmployeeService employeeService;
    private EmployeeRepository mockedEmployeeRepository;
    @BeforeEach
    void setUp(){
        mockedEmployeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(mockedEmployeeRepository);
    }
    @Test
    void should_return_created_employee_when_create_given_employee_service_and_employee_with_valid_age(){
        //Given
        Employee employee = new Employee(null, "Sam", 23, "Female", 1200, 1L);
        when(mockedEmployeeRepository.insert(employee)).thenReturn(employee);
        //When
        Employee employeeResponse = employeeService.create(employee);
        //Then
        assertEquals(employee.getId(),employeeResponse.getId());
        assertEquals("Sam",employeeResponse.getName());
        assertEquals(23,employeeResponse.getAge());
        assertEquals("Female",employeeResponse.getGender());
        assertEquals(1200,employeeResponse.getSalary());
    }
    @Test
    void should_throw_exception_when_create_given_employee_service_and_employee_age_is_less_than_18(){
        //Given
        Employee employee = new Employee(null, "Sam", 17, "Female", 1200, 1L);
        //When
        EmployeeCreateException employeeCreateException = assertThrows(EmployeeCreateException.class, () -> {
            employeeService.create(employee);
        });
        //Then
        assertEquals("Employee must be 18-65 years old", employeeCreateException.getMessage());
    }
}

