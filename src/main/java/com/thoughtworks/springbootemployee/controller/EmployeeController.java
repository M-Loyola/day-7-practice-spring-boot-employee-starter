package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> listAllEmployees() {
        return employeeService.listAllEmployees();
    }

    @GetMapping(path = "/{id}")
    public Employee findEmployeeById(@PathVariable Long id) {
        return employeeService.findEmployeeById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> findEmployeeByGender(@RequestParam String gender) {
        return employeeService.findEmployeeByGender(gender);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee newEmployee) {
        return employeeService.updateEmployee(id, newEmployee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Employee> listEmployeesByPage(@RequestParam Long pageNumber, @RequestParam Long pageSize) {
        return employeeService.listEmployeesByPage(pageNumber, pageSize);
    }
}