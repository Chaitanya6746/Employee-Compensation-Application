package com.project.employeecomp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.project.employeecomp.model.Employee;
import com.project.employeecomp.repository.EmployeeRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Employee> filterEmployees(String role, String location, boolean includeInactive) {
        return jdbcTemplate.query(
            "CALL FilterEmployees(?, ?, ?)",
            new Object[]{role.isEmpty() ? null : role, location.isEmpty() ? null : location, includeInactive},
            (rs, rowNum) -> {
                Employee emp = new Employee();
                emp.setId(rs.getLong("id"));
                emp.setName(rs.getString("name"));
                emp.setRole(rs.getString("role"));
                emp.setLocation(rs.getString("location"));
                emp.setCompensation(rs.getDouble("compensation"));
                emp.setYearsExperience(rs.getInt("years_experience"));
                emp.setStatus(rs.getString("status"));
                return emp;
            }
        );
    }

    public Double getAverageCompensation(String location) {
        return jdbcTemplate.queryForObject(
            "CALL CalculateAverageCompensation(?)",
            new Object[]{location.isEmpty() ? null : location},
            Double.class
        );
    }

    public List<Map<String, Object>> getCompensationByLocation() {
        List<Map<String, Object>> result = jdbcTemplate.queryForList("CALL GetCompensationByLocation()");
        return result != null ? result : Collections.emptyList();
    }

    public List<Map<String, Object>> getExperienceDistribution(String groupBy) {
        return jdbcTemplate.queryForList(
            "CALL GetExperienceDistribution(?)",
            new Object[]{groupBy.isEmpty() ? "all" : groupBy}
        );
    }

    public List<Employee> applyCompensationIncrement(List<Employee> employees, Double globalIncrement, 
                                                   Map<Long, Double> customIncrements) {
        return employees.stream().map(emp -> {
            Employee newEmp = new Employee();
            newEmp.setId(emp.getId());
            newEmp.setName(emp.getName());
            newEmp.setRole(emp.getRole());
            newEmp.setLocation(emp.getLocation());
            newEmp.setYearsExperience(emp.getYearsExperience());
            newEmp.setStatus(String.valueOf(emp.getStatus()));
            
            Double increment = customIncrements.getOrDefault(emp.getId(), globalIncrement);
            newEmp.setCompensation(emp.getCompensation() * (1 + (increment != null ? increment : 0)/100));
            return newEmp;
        }).collect(Collectors.toList());
    }

    public List<String> getDistinctRoles() {
        return employeeRepository.findDistinctRoles();
    }

    public List<String> getDistinctLocations() {
        return employeeRepository.findDistinctLocations();
    }
}