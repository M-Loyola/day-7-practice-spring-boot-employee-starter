package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private static final List<Employee> employees = new ArrayList<>();
    private static final Long START_ID_MINUS_ONE = 0L;
    private static final Long ID_INCREMENT = 1L;

    static {
        employees.add(new Employee(1L, "Alice", 30, "Female", 500, 1L));
        employees.add(new Employee(2L, "Bob", 23, "Male", 699, 2L));
        employees.add(new Employee(3L, "Sandra", 66, "Female", 788, 3L));
        employees.add(new Employee(4L, "Sam", 34, "Male", 4566, 5L));
        employees.add(new Employee(5L, "Aubrey", 21, "Male", 6900, 1L));
    }

    public List<Employee> listAllEmployees() {
        return employees;
    }

    public Employee findEmployeeById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findEmployeeByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee save(Employee employee) {
        Long id = generateNextId();
        Employee toBeSavedEmployee = new Employee(
                id,
                employee.getName(),
                employee.getAge(),
                employee.getGender(),
                employee.getSalary(),
                employee.getCompanyId()
        );
        employees.add(toBeSavedEmployee);
        return toBeSavedEmployee;
    }

    public Long generateNextId() {
        return employees.stream()
                .mapToLong(Employee::getId)
                .max()
                .orElse(START_ID_MINUS_ONE) + ID_INCREMENT;
    }

    public void deleteEmployee(Employee employee) {
        employees.remove(employee);
    }

    public List<Employee> listEmployeesByPage(Long pageNumber, Long pageSize) {
        return employees.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public List<Employee> findEmployeeByCompanyId(Long companyId) {
        return employees.stream()
                .filter(employee -> employee.getCompanyId().equals(companyId))
                .collect(Collectors.toList());
    }

    public Employee insert(Employee newEmployee) {
        newEmployee.setId(generateNextId());
        employees.add(newEmployee);
        return newEmployee;
    }

    public void cleanAll() {
        employees.clear();
    }
}
