package com.pgf.product_service.service;

import com.pgf.product_service.entity.Employee;
import com.pgf.product_service.exception.ResourceNotFoundException;
import com.pgf.product_service.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

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
        when(employeeRepository.save(any(Employee.class))).thenReturn(emp1);

        Employee saved = employeeService.createEmployee(emp1);

        assertNotNull(saved);
        assertEquals("John", saved.getFirstName());
        verify(employeeRepository, times(1)).save(emp1);
    }

    @Test
    void testSaveAllEmployees() {
        List<Employee> employeeList = Arrays.asList(emp1, emp2);
        when(employeeRepository.saveAll(employeeList)).thenReturn(employeeList);

        List<Employee> savedList = employeeService.saveAllEmployees(employeeList);

        assertNotNull(savedList);
        assertEquals(2, savedList.size());
        verify(employeeRepository, times(1)).saveAll(employeeList);
    }

    @Test
    void testGetAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(emp1, emp2));

        List<Employee> list = employeeService.getAllEmployees();

        assertEquals(2, list.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetEmployeeById_Success() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp1));

        Employee found = employeeService.getEmployeeById(1L);

        assertEquals("John", found.getFirstName());
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(99L));
    }

    @Test
    void testUpdateEmployee() {
        Employee updatedDetails = Employee.builder()
                .firstName("Johnny")
                .lastName("Doe")
                .email("johnny.doe@example.com")
                .department("Engineering")
                .salary(80000.0)
                .build();

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp1));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Employee result = employeeService.updateEmployee(1L, updatedDetails);

        assertEquals("Johnny", result.getFirstName());
        assertEquals("johnny.doe@example.com", result.getEmail());
        assertEquals(80000.0, result.getSalary());
    }

    @Test
    void testDeleteEmployee_Success() {
        when(employeeRepository.existsById(1L)).thenReturn(true);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteEmployee_NotFound() {
        when(employeeRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> employeeService.deleteEmployee(99L));
    }
}
