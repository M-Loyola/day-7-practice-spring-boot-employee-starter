package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.exception.EmployeeIsInactiveException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee create(Employee employee) {
        if (employee.hasInvalidAge(employee)) {
            throw new EmployeeCreateException();
        }
        employee.setEmploymentStatus(true);
        return employeeRepository.insert(employee);
    }

    public void delete(Employee employee) {
        employee.setEmploymentStatus(false);
    }

    public void update(Employee employee, Integer newAge, Integer newSalary) {
        if (!employee.isEmploymentStatus()) {
            throw new EmployeeIsInactiveException();
        }
        employee.setAge(newAge);
        employee.setSalary(newSalary);
    }
}
