package com.pgf.product_service.controller;

import com.pgf.product_service.entity.Employee;
import com.pgf.product_service.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private Employee emp1;
    private Employee emp2;

    @BeforeEach
    void setUp() {
        emp1 = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .department("Engineering")
                .salary(75000.0)
                .build();

        emp2 = Employee.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .department("Marketing")
                .salary(68000.0)
                .build();
    }

    @Test
    void testCreateEmployee() {
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(emp1);

        ResponseEntity<Employee> response = employeeController.createEmployee(emp1);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John", response.getBody().getFirstName());
    }

    @Test
    void testSaveAllEmployees() {
        List<Employee> list = Arrays.asList(emp1, emp2);
        when(employeeService.saveAllEmployees(list)).thenReturn(list);

        ResponseEntity<List<Employee>> response = employeeController.saveAllEmployees(list);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetAllEmployees() {
        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(emp1, emp2));

        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetEmployeeById() {
        when(employeeService.getEmployeeById(1L)).thenReturn(emp1);

        ResponseEntity<Employee> response = employeeController.getEmployeeById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John", response.getBody().getFirstName());
    }

    @Test
    void testUpdateEmployee() {
        when(employeeService.updateEmployee(eq(1L), any(Employee.class))).thenReturn(emp1);

        ResponseEntity<Employee> response = employeeController.updateEmployee(1L, emp1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John", response.getBody().getFirstName());
    }

    @Test
    void testDeleteEmployee() {
        ResponseEntity<Void> response = employeeController.deleteEmployee(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(employeeService, times(1)).deleteEmployee(1L);
    }
}
