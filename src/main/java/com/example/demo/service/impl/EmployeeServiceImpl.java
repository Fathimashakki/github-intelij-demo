package com.example.demo.service.impl;

import java.util.List;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.EmployeeNameIdDto;
import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;

import com.example.demo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto dto) {
        Employee employee = new Employee();
        populateEmployee(employee, dto);
        return EmployeeMapper.mapToDto(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto dto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        populateEmployee(employee, dto);
        return EmployeeMapper.mapToDto(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDto updateEmployeeDepartment(Long id, EmployeeDto dto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        if (dto.getDepartmentName() == null) {
            throw new IllegalArgumentException("Department name is required to update department.");
        }

        Department department = departmentRepository.findByName(dto.getDepartmentName());
        if (department == null) {
            throw new ResourceNotFoundException("Department not found with name: " + dto.getDepartmentName());
        }

        employee.setDepartment(department);
        return EmployeeMapper.mapToDto(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return EmployeeMapper.mapToDto(employee);
    }

    @Override
    public Page<EmployeeDto> getAllEmployees(int page) {
        return employeeRepository.findAll(PageRequest.of(page, 20))
                .map(EmployeeMapper::mapToDto);
    }

    @Override
    public Page<EmployeeNameIdDto> getEmployeeNamesAndIds(int page) {
        return employeeRepository.findAll(PageRequest.of(page, 20))
                .map(EmployeeMapper::mapToNameIdDto);
    }

    @Override
    public Page<EmployeeDto> searchEmployees(String name, int page) {
        List<Employee> filtered = employeeRepository.findByNameContainingIgnoreCase(name);
        List<EmployeeDto> result = filtered.stream().map(EmployeeMapper::mapToDto).toList();
        return new PageImpl<>(result, PageRequest.of(page, 20), result.size());
    }

    private void populateEmployee(Employee employee, EmployeeDto dto) {
        employee.setName(dto.getName());
        employee.setDateOfBirth(dto.getDateOfBirth());
        employee.setSalary(dto.getSalary());
        employee.setAddress(dto.getAddress());
        employee.setRole(dto.getRole());
        employee.setJoiningDate(dto.getJoiningDate());
        employee.setYearlyBonusPercentage(dto.getYearlyBonusPercentage());

        if (dto.getDepartmentName() != null) {
            Department department = departmentRepository.findByName(dto.getDepartmentName());
            if (department == null) {
                throw new ResourceNotFoundException("Department not found with name: " + dto.getDepartmentName());
            }
            employee.setDepartment(department);
        }

        if (dto.getReportingManagerName() != null) {
            List<Employee> matches = employeeRepository.findByNameContainingIgnoreCase(dto.getReportingManagerName());
            if (matches.isEmpty()) {
                throw new ResourceNotFoundException("Reporting manager not found with name: " + dto.getReportingManagerName());
            }
            employee.setReportingManager(matches.get(0));
        }
    }
}


