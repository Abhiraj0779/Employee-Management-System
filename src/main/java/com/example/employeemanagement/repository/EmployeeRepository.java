package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("select e from Employee e where lower(e.firstName) like lower(concat('%', :q, '%')) " +
           "or lower(e.lastName) like lower(concat('%', :q, '%')) " +
           "or lower(e.email) like lower(concat('%', :q, '%')) " +
           "or lower(e.department) like lower(concat('%', :q, '%')) " +
           "or lower(e.designation) like lower(concat('%', :q, '%')) order by e.lastName, e.firstName")
    List<Employee> search(@Param("q") String query);
    List<Employee> findAllByOrderByLastNameAscFirstNameAsc();
}
