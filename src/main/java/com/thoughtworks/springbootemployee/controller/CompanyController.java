package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public CompanyController(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }
    @GetMapping
    public List<Company> listAllCompanies() {
        return companyRepository.listAllCompanies();
    }
    @GetMapping(path = "/{id}")
    public Company findById(@PathVariable Long id) {
        return companyRepository.findById(id);
    }
    @GetMapping("/{companyId}/employees")
    public List<Employee> getEmployeesByCompanyId(@PathVariable Long companyId) {
        return employeeRepository.findByCompanyId(companyId);
    }
    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Company> listByPage(@RequestParam Long pageNumber, @RequestParam Long pageSize) {
        return companyRepository.listByPage(pageNumber, pageSize);
    }
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Company addCompany(@RequestBody Company company){
        return companyRepository.save(company);

    }
    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable Long id, @RequestBody Company updatedCompanyInfo) {
        Company company = companyRepository.findById(id);
        company.setName(updatedCompanyInfo.getName());
        return company;
    }
    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable Long id) {
        Company company = companyRepository.findById(id);
        if (company == null) {
            throw new CompanyNotFoundException();
        }
        companyRepository.delete(company);
    }
}
