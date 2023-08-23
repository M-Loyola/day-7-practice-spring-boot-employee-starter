package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.exception.EmployeeIsInactiveException;
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
    void setUp() {
        mockedEmployeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(mockedEmployeeRepository);
    }

    @Test
    void should_return_created_employee_when_create_given_employee_service_and_employee_with_valid_age() {
        //Given
        Employee employee = new Employee(null, "Sam", 23, "Female", 1200, 1L);
        when(mockedEmployeeRepository.insert(employee)).thenReturn(employee);
        //When
        Employee employeeResponse = employeeService.create(employee);
        //Then
        assertEquals(employee.getId(), employeeResponse.getId());
        assertEquals("Sam", employeeResponse.getName());
        assertEquals(23, employeeResponse.getAge());
        assertEquals("Female", employeeResponse.getGender());
        assertEquals(1200, employeeResponse.getSalary());
    }

    @Test
    void should_throw_exception_when_create_given_employee_service_and_employee_age_is_less_than_18() {
        //Given
        Employee employee = new Employee(null, "Sam", 17, "Female", 1200, 1L);
        //When
        EmployeeCreateException employeeCreateException = assertThrows(EmployeeCreateException.class, () -> {
            employeeService.create(employee);
        });
        //Then
        assertEquals("Employee must be 18-65 years old", employeeCreateException.getMessage());
    }

    @Test
    void should_throw_exception_when_create_given_employee_service_and_employee_age_is_more_than_65() {
        //Given
        Employee employee = new Employee(null, "Sam", 77, "Female", 1200, 1L);
        //When
        EmployeeCreateException employeeCreateException = assertThrows(EmployeeCreateException.class, () -> {
            employeeService.create(employee);
        });
        //Then
        assertEquals("Employee must be 18-65 years old", employeeCreateException.getMessage());
    }

    @Test
    void should_return_new_employee_with_active_status_true_when_create_given_employee_service_and_employee() {
        //Given
        Employee employee = new Employee(null, "Sam", 23, "Female", 1200, 1L);
        when(mockedEmployeeRepository.insert(employee)).thenReturn(employee);
        //When
        Employee employeeResponse = employeeService.create(employee);
        //Then
        assertEquals(employee.getId(), employeeResponse.getId());
        assertEquals("Sam", employeeResponse.getName());
        assertEquals(23, employeeResponse.getAge());
        assertEquals("Female", employeeResponse.getGender());
        assertEquals(1200, employeeResponse.getSalary());
        assertEquals(true, employeeResponse.isEmploymentStatus());
    }

    @Test
    void should_return_employee_with_active_status_false_when_delete_given_employee_service_and_employee() {
        //Given
        Employee employee = new Employee(null, "Sam", 23, "Female", 1200, 1L);
        when(mockedEmployeeRepository.insert(employee)).thenReturn(employee);
        //When
        Employee employeeResponse = employeeService.create(employee);
        employeeService.delete(employee);
        //Then
        assertEquals(employee.getId(), employeeResponse.getId());
        assertEquals("Sam", employeeResponse.getName());
        assertEquals(23, employeeResponse.getAge());
        assertEquals("Female", employeeResponse.getGender());
        assertEquals(1200, employeeResponse.getSalary());
        assertEquals(false, employeeResponse.isEmploymentStatus());
    }

    @Test
    void should_return_updated_employee_when_update_given_employee_service_and_employee_with_active_status_true() {
        // Given
        Employee employee = new Employee(null, "Sam", 23, "Female", 1200, 1L);
        Integer newAge = 24;
        Integer newSalary = 1500;
        Employee updatedEmployee = new Employee(null, "Sam", newAge, "Female", newSalary, 1L);

        when(mockedEmployeeRepository.insert(employee)).thenReturn(employee);
        when(mockedEmployeeRepository.save(updatedEmployee)).thenReturn(updatedEmployee);

        // When
        Employee employeeResponse = employeeService.create(employee);
        employeeService.update(employee, newAge, newSalary);

        // Then
        assertEquals(employee.getId(), employeeResponse.getId());
        assertEquals("Sam", employeeResponse.getName());
        assertEquals(newAge, employeeResponse.getAge());
        assertEquals("Female", employeeResponse.getGender());
        assertEquals(newSalary, employeeResponse.getSalary());
        assertEquals(true, employeeResponse.isEmploymentStatus());
    }

    @Test
    void should_throw_exception_message_when_update_given_employee_service_and_employee_with_active_status_false() {
        // Given
        Employee employee = new Employee(null, "Sam", 23, "Female", 1200, 1L);
        employee.setEmploymentStatus(false);
        Integer newAge = 24;
        Integer newSalary = 1500;

        // When
        EmployeeIsInactiveException employeeIsInactiveException = assertThrows(EmployeeIsInactiveException.class, () -> {
            employeeService.update(employee, newAge, newSalary);
        });

        // Then
        assertEquals("Employee is inactive", employeeIsInactiveException.getMessage());
    }
}

