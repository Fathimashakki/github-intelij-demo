package com.example.demo.mapper;

import com.example.demo.dto.DepartmentDto;
import com.example.demo.entity.Department;

import java.util.stream.Collectors;

public class DepartmentMapper {


    public static DepartmentDto mapToDto(Department dept, boolean expandEmployees) {
        if (dept == null) return null;

        DepartmentDto dto = new DepartmentDto();
        dto.setId(dept.getId());
        dto.setName(dept.getName());
        dto.setCreationDate(dept.getCreationDate());

        if (dept.getHead() != null) {
            dto.setHeadName(dept.getHead().getName());
        }

        if (expandEmployees && dept.getEmployees() != null) {
            dto.setEmployees(
                    dept.getEmployees().stream()
                            .map(EmployeeMapper::mapToDto)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }
}
