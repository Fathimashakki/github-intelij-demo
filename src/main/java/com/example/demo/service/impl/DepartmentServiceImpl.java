package com.example.demo.service.impl;

import com.example.demo.dto.DepartmentDto;
import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.DepartmentMapper;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public DepartmentDto createDepartment(DepartmentDto dto) {
        Department department = new Department();
        department.setName(dto.getName());
        department.setCreationDate(dto.getCreationDate());

        if (dto.getHeadName() != null) {
            List<Employee> matches = employeeRepository.findByNameContainingIgnoreCase(dto.getHeadName());
            if (matches.isEmpty()) {
                throw new ResourceNotFoundException("Head employee not found: " + dto.getHeadName());
            }
            department.setHead(matches.get(0));
        }

        return DepartmentMapper.mapToDto(departmentRepository.save(department), false);
    }

    @Override
    public DepartmentDto updateDepartment(Long id, DepartmentDto dto) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        department.setName(dto.getName());
        department.setCreationDate(dto.getCreationDate());

        if (dto.getHeadName() != null) {
            List<Employee> matches = employeeRepository.findByNameContainingIgnoreCase(dto.getHeadName());
            if (matches.isEmpty()) {
                throw new ResourceNotFoundException("Head employee not found: " + dto.getHeadName());
            }
            department.setHead(matches.get(0));
        }

        return DepartmentMapper.mapToDto(departmentRepository.save(department), false);
    }

    @Override
    public void deleteDepartment(Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        if (dept.getEmployees() != null && !dept.getEmployees().isEmpty()) {
            throw new RuntimeException("Cannot delete department with employees assigned.");
        }

        departmentRepository.delete(dept);
    }

    @Override
    public DepartmentDto getDepartmentById(Long id, boolean expandEmployees) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        return DepartmentMapper.mapToDto(dept, expandEmployees);
    }

    @Override
    public Page<DepartmentDto> getAllDepartments(int page, boolean expandEmployees) {
        return departmentRepository.findAll(PageRequest.of(page, 20))
                .map(dept -> DepartmentMapper.mapToDto(dept, expandEmployees));
    }
}
