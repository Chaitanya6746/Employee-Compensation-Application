Employee Compensation System
A Spring Boot web application for managing employee compensation data, developed by Kalyan. The system allows users to filter employees by role and location using dropdowns, visualize compensation trends with charts, analyze experience distribution, simulate compensation increments, and export data as CSV. The application features a modern, responsive UI with polished CSS styling.
Features

Employee Filtering: Filter employees by role (e.g., Software Engineer, Product Manager) and location (e.g., New York, San Francisco) using intuitive dropdowns, with an option to include inactive employees.
Compensation Visualization: View average compensation by location with interactive bar charts powered by Chart.js.
Experience Distribution: Analyze employee experience distribution by role or location in tabular format.
Compensation Simulation: Simulate global or custom compensation increments for employees.
CSV Export: Export filtered employee data as a CSV file for further analysis.
Modern UI: Clean, responsive design with gradients, shadows, and hover effects, styled using Tailwind CSS and custom styles.css.

Technologies

Backend: Spring Boot, Spring Data JPA, Spring JDBC
Frontend: Thymeleaf, Tailwind CSS, Chart.js
Database: MySQL with stored procedures
Build Tool: Maven
Language: Java 17

Project Structure
Employee-Compensation-Application/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/project/employeecomp/
│   │   │   ├── controller/EmployeeController.java
│   │   │   ├── model/Employee.java
│   │   │   ├── repository/EmployeeRepository.java
│   │   │   ├── service/EmployeeService.java
│   │   ├── resources/
│   │   │   ├── static/css/styles.css
│   │   │   ├── templates/
│   │   │   │   ├── employees.html
│   │   │   │   ├── experience.html
│   │   │   │   ├── simulation.html
│   │   │   ├── application.properties.template
├── Employee.sql
├── .gitignore
├── README.md


employee_comp.sql: MySQL script to create the database, table, sample data, and stored procedures.
application.properties.template: Template for database configuration.
styles.css: Custom CSS for modern styling.
employees.html: Main page with dropdown filters and charts.
experience.html: Experience distribution analysis.
simulation.html: Compensation increment simulation.

Prerequisites

Java: 17 or higher
Maven: For dependency management and building
MySQL: For the database
Git: To clone the repository
Browser: Chrome, Firefox, or Edge for the web interface

Setup Instructions
Follow these steps to set up and run the project locally:

Clone the Repository:
git clone https://github.com/Chaitanya6746/Employee-Compensation-Application.git
cd Employee-Compensation-Application


Set Up the Database:

Install MySQL (if not already installed).
Log in to MySQL:mysql -u root -p


Run the employee_comp.sql script to create the employee_comp database, employees table, sample data (15 records), and stored procedures:SOURCE employee_comp.sql;


Verify the database:USE employee_comp;
SELECT * FROM employees;




Configure the Application:

Copy the template to create application.properties:cp src/main/resources/application.properties.template src/main/resources/application.properties


Edit application.properties to add your MySQL credentials:spring.datasource.url=jdbc:mysql://localhost:3306/employee_comp
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=none
spring.thymeleaf.cache=false
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

Replace your_username and your_password with your MySQL credentials.


Build and Run the Application:

Build the project:mvn clean install


Run the application: Americanamvn spring-boot:run


Access the application at http://localhost:8080/ in your browser.



Usage

Employees Page (http://localhost:8080/):
Use dropdowns to filter employees by role (e.g., Data Analyst, Software Engineer) and location (e.g., Boston, Chicago).
Check “Include Inactive” to show inactive employees.
View average compensation and a bar chart of compensation by location.
Export filtered data as a CSV file.


Experience Distribution (http://localhost:8080/experience):
Select “All”, “By Location”, or “By Role” to view experience distribution (e.g., 0-1 years, 2-5 years).
Data is displayed in a table.


Compensation Simulation (http://localhost:8080/simulation):
Enter a global increment percentage or custom increments for individual employees.
Submit to view updated compensation values.



Screenshots
(Optional: Add screenshots here after deploying the app, e.g., employees page with dropdowns, chart, or simulation table. Use GitHub’s image upload feature.)
Contributing
Contributions are welcome! To contribute:

Fork the repository.
Create a feature branch:git checkout -b feature/your-feature


Commit changes:git commit -m "Add your feature"


Push to your fork:git push origin feature/your-feature


Open a pull request on https://github.com/Chaitanya6746/Employee-Compensation-Application.

License
This project is licensed under the MIT License. See the LICENSE file for details.
Contact
Developed by Kalyan. For questions or feedback, open an issue on GitHub or contact via GitHub profile: Chaitanya6746.

Last updated: May 18, 2025
