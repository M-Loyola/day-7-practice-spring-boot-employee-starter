package com.thoughtworks.springbootemployee.controller;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private static final List<Company> companies = new ArrayList<>();
    private static final long START_ID_MINUS_ONE = 0L;
    private static final long ID_INCREMENT = 1L;

    static {
        companies.add(new Company(1, "Spring"));
        companies.add(new Company(2, "Boot"));
        companies.add(new Company(3, "Spring Boot"));
        companies.add(new Company(4, "Boot Project"));
        companies.add(new Company(5, "Spring Project"));
    }

    public List<Company> listAllCompanies() {
        return companies;
    }

    public Company findCompanyById(Long id) {
        return companies.stream()
                .filter(company -> company.getId() == id)
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);
    }

    public List<Company> listCompaniesByPage(Long pageNumber, Long pageSize) {
        return companies.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company save(Company company) {
        Long id = generateNextId();
        Company toBeSavedCompany = new Company(id, company.getName());
        companies.add(toBeSavedCompany);
        return toBeSavedCompany;
    }

    public Long generateNextId() {
        return companies.stream()
                .mapToLong(Company::getId)
                .max()
                .orElse(START_ID_MINUS_ONE) + ID_INCREMENT;
    }

    public void deleteCompany(Company company) {
        companies.remove(company);
    }
}
