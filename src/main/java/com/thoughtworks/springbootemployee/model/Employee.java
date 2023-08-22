package com.thoughtworks.springbootemployee.model;

public class Employee {
    private final long id;
    private final String name;
    private int age;
    private final String gender;
    private int salary;
    private final long companyId;

    public Employee(long id, String name, int age, String gender, int salary, long companyId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.companyId = companyId;
    }

    public long getCompanyId() {
        return companyId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
