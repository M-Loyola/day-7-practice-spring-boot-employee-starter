package com.thoughtworks.springbootemployee.controller;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class CompanyRepository {
    private static final List<Company> companies = new ArrayList<>();

    static {
        companies.add(new Company(1,"Spring"));
        companies.add(new Company(2,"Boot"));
        companies.add(new Company(3,"Spring Boot"));
        companies.add(new Company(4,"Boot Project"));
        companies.add(new Company(5,"Spring Project"));
    }
    public List<Company> listAllCompanies() {
        return companies;
    }
}
