package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/employees")
public class HelloController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping(path = "/{userName}")
    public String getAll(@PathVariable String userName) {

        return "Hello:" + userName;
    }

    @GetMapping
    public List<Employee> listAll() {
        return employeeRepository.listAll();
    }
}
