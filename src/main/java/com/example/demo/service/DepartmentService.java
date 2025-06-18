package com.example.demo.service;

import com.example.demo.dto.DepartmentDto;
import org.springframework.data.domain.Page;

public interface DepartmentService {

    DepartmentDto createDepartment(DepartmentDto dto);

    DepartmentDto updateDepartment(Long id, DepartmentDto dto);

    void deleteDepartment(Long id);

    DepartmentDto getDepartmentById(Long id, boolean expandEmployees);

    Page<DepartmentDto> getAllDepartments(int page, boolean expandEmployees);
}
