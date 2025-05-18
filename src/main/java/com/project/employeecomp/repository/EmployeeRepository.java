package com.project.employeecomp.repository;

import com.project.employeecomp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT DISTINCT e.role FROM Employee e WHERE e.role IS NOT NULL ORDER BY e.role")
    List<String> findDistinctRoles();

    @Query("SELECT DISTINCT e.location FROM Employee e WHERE e.location IS NOT NULL ORDER BY e.location")
    List<String> findDistinctLocations();
}