package com.pgf.product_service.service;

import com.pgf.product_service.entity.Employee;
import com.pgf.product_service.exception.ResourceNotFoundException;
import com.pgf.product_service.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Save a single employee.
     */
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * Save a list of employees to the database.
     */
    public List<Employee> saveAllEmployees(List<Employee> employees) {
        return employeeRepository.saveAll(employees);
    }

    /**
     * Retrieve all employees.
     */
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Retrieve an employee by ID.
     */
    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    /**
     * Update an existing employee.
     */
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmployee = getEmployeeById(id);
        existingEmployee.setFirstName(updatedEmployee.getFirstName());
        existingEmployee.setLastName(updatedEmployee.getLastName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        existingEmployee.setSalary(updatedEmployee.getSalary());
        return employeeRepository.save(existingEmployee);
    }

    /**
     * Delete an employee by ID.
     */
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }
}
