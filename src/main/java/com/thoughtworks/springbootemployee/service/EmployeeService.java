package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.exception.EmployeeIsInactiveException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> listAllEmployees() {
        return employeeRepository.listAllEmployees();
    }

    public Employee findEmployeeById(Long id) {
        return employeeRepository.findEmployeeById(id);
    }

    public List<Employee> findEmployeeByGender(String gender) {
        return employeeRepository.findEmployeeByGender(gender);
    }

    public Employee addEmployee(Employee employee) {
        if (employee.hasInvalidAge()) {
            throw new EmployeeCreateException();
        }
        employee.setEmploymentStatus(true);
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee newEmployee) {
        Employee existingEmployee = findEmployeeById(id);
        if (!existingEmployee.isEmploymentStatus()) {
            throw new EmployeeIsInactiveException();
        }
        existingEmployee.setAge(newEmployee.getAge());
        existingEmployee.setSalary(newEmployee.getSalary());
        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = findEmployeeById(id);
        employee.setEmploymentStatus(false);
        employeeRepository.save(employee);
    }

    public List<Employee> listEmployeesByPage(Long pageNumber, Long pageSize) {
        return employeeRepository.listEmployeesByPage(pageNumber, pageSize);
    }
}