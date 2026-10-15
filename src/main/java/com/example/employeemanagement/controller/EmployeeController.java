package com.example.employeemanagement.controller;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.LinkedHashSet;
import java.util.List;

@Controller
public class EmployeeController {
    private final EmployeeRepository employees;
    public EmployeeController(EmployeeRepository employees) { this.employees = employees; }

    @GetMapping("/")
    public String home() { return "redirect:/employees"; }

    @GetMapping("/employees")
    public String list(@RequestParam(required = false) String q, Model model) {
        List<Employee> results = (q == null || q.isBlank())
                ? employees.findAllByOrderByLastNameAscFirstNameAsc()
                : employees.search(q.trim());
        model.addAttribute("employees", results);
        model.addAttribute("query", q == null ? "" : q);
        model.addAttribute("totalEmployees", employees.count());
        model.addAttribute("departments", new LinkedHashSet<>(employees.findAll().stream().map(Employee::getDepartment).toList()).size());
        model.addAttribute("pageTitle", "Employees");
        return "employees/list";
    }

    @GetMapping("/employees/new")
    public String createForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("pageTitle", "Add employee");
        return "employees/form";
    }

    @GetMapping("/employees/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        return employees.findById(id).map(employee -> {
            model.addAttribute("employee", employee);
            model.addAttribute("pageTitle", "Edit employee");
            return "employees/form";
        }).orElseGet(() -> {
            redirect.addFlashAttribute("errorMessage", "Employee not found.");
            return "redirect:/employees";
        });
    }

    @PostMapping("/employees")
    public String save(@Valid @ModelAttribute("employee") Employee employee,
                       BindingResult result, Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", employee.getId() == null ? "Add employee" : "Edit employee");
            return "employees/form";
        }
        try {
            boolean isNew = employee.getId() == null;
            employees.save(employee);
            redirect.addFlashAttribute("successMessage", isNew ? "Employee added successfully." : "Employee details updated successfully.");
            return "redirect:/employees";
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            result.rejectValue("email", "duplicate", "This email address is already in use.");
            model.addAttribute("pageTitle", employee.getId() == null ? "Add employee" : "Edit employee");
            return "employees/form";
        }
    }

    @PostMapping("/employees/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        if (employees.existsById(id)) {
            employees.deleteById(id);
            redirect.addFlashAttribute("successMessage", "Employee deleted successfully.");
        } else {
            redirect.addFlashAttribute("errorMessage", "Employee not found.");
        }
        return "redirect:/employees";
    }
}
