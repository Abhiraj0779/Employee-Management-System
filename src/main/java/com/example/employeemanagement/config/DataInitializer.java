package com.example.employeemanagement.config;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seedEmployees(EmployeeRepository repository) {
        return args -> {
            if (repository.count() > 0) return;
            repository.save(make("Aarav", "Sharma", "aarav.sharma@example.com", "Engineering", "Software Engineer", "65000", "2023-07-10"));
            repository.save(make("Diya", "Patil", "diya.patil@example.com", "Human Resources", "HR Executive", "48000", "2022-11-01"));
            repository.save(make("Rohan", "Deshmukh", "rohan.deshmukh@example.com", "Finance", "Accountant", "52000", "2024-02-12"));
            repository.save(make("Ananya", "Kulkarni", "ananya.kulkarni@example.com", "Engineering", "QA Analyst", "57000", "2023-04-18"));
        };
    }
    private Employee make(String first, String last, String email, String department, String designation, String salary, String date) {
        Employee e = new Employee();
        e.setFirstName(first); e.setLastName(last); e.setEmail(email); e.setDepartment(department);
        e.setDesignation(designation); e.setSalary(new BigDecimal(salary)); e.setJoiningDate(LocalDate.parse(date));
        return e;
    }
}
