package com.project.employeecomp.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.project.employeecomp.model.Employee;
import com.project.employeecomp.service.EmployeeService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public String showEmployees(Model model) {
        model.addAttribute("employees", employeeService.filterEmployees("", "", false));
        model.addAttribute("locations", employeeService.getCompensationByLocation());
        model.addAttribute("roles", employeeService.getDistinctRoles());
        model.addAttribute("locationsList", employeeService.getDistinctLocations());
        model.addAttribute("role", "");
        model.addAttribute("location", "");
        model.addAttribute("includeInactive", false);
        return "employees";
    }

    @PostMapping("/filter")
    public String filterEmployees(
            @RequestParam String role,
            @RequestParam String location,
            @RequestParam(defaultValue = "false") boolean includeInactive,
            Model model) {
        model.addAttribute("employees", employeeService.filterEmployees(role, location, includeInactive));
        model.addAttribute("avgCompensation", employeeService.getAverageCompensation(location));
        model.addAttribute("locations", employeeService.getCompensationByLocation());
        model.addAttribute("roles", employeeService.getDistinctRoles());
        model.addAttribute("locationsList", employeeService.getDistinctLocations());
        model.addAttribute("role", role);
        model.addAttribute("location", location);
        model.addAttribute("includeInactive", includeInactive);
        return "employees";
    }

    @GetMapping("/experience")
    public String showExperience(@RequestParam(defaultValue = "") String groupBy, Model model) {
        model.addAttribute("distributions", employeeService.getExperienceDistribution(groupBy));
        return "experience";
    }

    @GetMapping("/simulation")
    public String showSimulation(Model model) {
        model.addAttribute("employees", employeeService.filterEmployees("", "", false));
        return "simulation";
    }

    @PostMapping("/simulate")
    public String simulateIncrements(
            @RequestParam Double globalIncrement,
            @RequestParam Map<String, String> allParams,
            Model model) {
        List<Employee> employees = employeeService.filterEmployees("", "", false);
        Map<Long, Double> customIncrements = new HashMap<>();
        
        allParams.forEach((key, value) -> {
            if (key.startsWith("customIncrement_")) {
                try {
                    Long empId = Long.parseLong(key.split("_")[1]);
                    customIncrements.put(empId, Double.parseDouble(value));
                } catch (NumberFormatException e) {
                    // Ignore invalid inputs
                }
            }
        });

        model.addAttribute("employees", 
            employeeService.applyCompensationIncrement(employees, globalIncrement, customIncrements));
        return "simulation";
    }

    @GetMapping("/export")
    public void exportCSV(
            @RequestParam String role,
            @RequestParam String location,
            @RequestParam(defaultValue = "false") boolean includeInactive,
            HttpServletResponse response) throws IOException {
        List<Employee> employees = employeeService.filterEmployees(role, location, includeInactive);
        
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=employees.csv");

        PrintWriter writer = response.getWriter();
        writer.write("Name,Role,Location,Experience,Compensation,Status\n");
        
        for (Employee emp : employees) {
            writer.write(String.format("%s,%s,%s,%d,%.2f,%s\n",
                    emp.getName(), emp.getRole(), emp.getLocation(),
                    emp.getYearsExperience(), emp.getCompensation(), emp.getStatus()));
        }
    }
}