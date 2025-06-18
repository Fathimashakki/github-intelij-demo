package com.example.demo.service;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.EmployeeNameIdDto;
import org.springframework.data.domain.Page;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto dto);
    EmployeeDto updateEmployee(Long id, EmployeeDto dto);
    EmployeeDto updateEmployeeDepartment(Long employeeId, EmployeeDto dto);
    EmployeeDto getEmployeeById(Long id);
    Page<EmployeeDto> getAllEmployees(int page);
    Page<EmployeeNameIdDto> getEmployeeNamesAndIds(int page);
    Page<EmployeeDto> searchEmployees(String name, int page);
}
