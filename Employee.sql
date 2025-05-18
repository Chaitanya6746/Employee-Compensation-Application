CREATE DATABASE IF NOT EXISTS employee_comp;

USE employee_comp;

CREATE TABLE employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL,
    location VARCHAR(50) NOT NULL,
    compensation DECIMAL(10,2) NOT NULL,
    years_experience INT NOT NULL,
    status ENUM('Active', 'Inactive') NOT NULL
);

USE employee_comp;

SELECT DISTINCT e.role FROM Employees e WHERE e.role IS NOT NULL ORDER BY e.role;

INSERT INTO employees (name, role, location, compensation, years_experience, status) VALUES
('John Smith', 'Software Engineer', 'New York', 85000.00, 3, 'Active'),
('Emma Johnson', 'Product Manager', 'San Francisco', 120000.00, 5, 'Active'),
('Michael Chen', 'Data Analyst', 'Chicago', 75000.00, 1, 'Active'),
('Sarah Davis', 'Software Engineer', 'San Francisco', 90000.00, 4, 'Active'),
('David Wilson', 'HR Specialist', 'New York', 65000.00, 2, 'Inactive'),
('Lisa Brown', 'Product Manager', 'Chicago', 110000.00, 6, 'Active'),
('James Taylor', 'Software Engineer', 'Boston', 80000.00, 2, 'Active'),
('Emily White', 'Data Analyst', 'New York', 70000.00, 0, 'Active'),
('Robert Lee', 'HR Specialist', 'San Francisco', 68000.00, 3, 'Active'),
('Anna Martinez', 'Software Engineer', 'Chicago', 82000.00, 7, 'Inactive'),
('Thomas Clark', 'Product Manager', 'Boston', 115000.00, 8, 'Active'),
('Olivia Harris', 'Data Analyst', 'San Francisco', 78000.00, 2, 'Active'),
('William Walker', 'Software Engineer', 'New York', 87000.00, 5, 'Active'),
('Sophia Adams', 'HR Specialist', 'Boston', 67000.00, 1, 'Active'),
('Daniel Young', 'Data Analyst', 'Chicago', 72000.00, 4, 'Active');


DELIMITER //

CREATE PROCEDURE FilterEmployees(
    IN roleFilter VARCHAR(50),
    IN locationFilter VARCHAR(50),
    IN includeInactive BOOLEAN
)
BEGIN
    SELECT id, name, role, location, compensation, years_experience, status
    FROM employees
    WHERE (roleFilter IS NULL OR role = roleFilter)
    AND (locationFilter IS NULL OR location = locationFilter)
    AND (includeInactive = TRUE OR status = 'Active');
END //

CREATE PROCEDURE CalculateAverageCompensation(
    IN locationFilter VARCHAR(50)
)
BEGIN
    SELECT AVG(compensation) as avg_compensation
    FROM employees
    WHERE (locationFilter IS NULL OR location = locationFilter)
    AND status = 'Active';
END //

CREATE PROCEDURE GetCompensationByLocation()
BEGIN
    SELECT location, AVG(compensation) as avg_compensation
    FROM employees
    WHERE status = 'Active'
    GROUP BY location;
END //

CREATE PROCEDURE GetExperienceDistribution(
    IN groupByField VARCHAR(50)
)
BEGIN
    SELECT 
        CASE 
            WHEN years_experience < 1 THEN '0-1 years'
            WHEN years_experience < 2 THEN '1-2 years'
            WHEN years_experience < 5 THEN '2-5 years'
            ELSE '5+ years'
        END as experience_range,
        IF(groupByField = 'location', location, 
           IF(groupByField = 'role', role, 'All')) as group_field,
        COUNT(*) as employee_count
    FROM employees
    WHERE status = 'Active'
    GROUP BY experience_range, group_field
    ORDER BY experience_range, group_field;
END //

DELIMITER ;