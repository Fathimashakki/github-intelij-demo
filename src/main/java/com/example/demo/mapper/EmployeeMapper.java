package com.example.demo.mapper;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.EmployeeNameIdDto;
import com.example.demo.entity.Employee;

public class EmployeeMapper {


    public static EmployeeDto mapToDto(Employee employee) {
        if (employee == null) return null;

        EmployeeDto dto = new EmployeeDto();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setDateOfBirth(employee.getDateOfBirth());
        dto.setSalary(employee.getSalary());
        dto.setAddress(employee.getAddress());
        dto.setRole(employee.getRole());
        dto.setJoiningDate(employee.getJoiningDate());
        dto.setYearlyBonusPercentage(employee.getYearlyBonusPercentage());

        if (employee.getDepartment() != null) {
            dto.setDepartmentName(employee.getDepartment().getName());
        }

        if (employee.getReportingManager() != null) {
            dto.setReportingManagerName(employee.getReportingManager().getName());
        }

        return dto;
    }


    public static EmployeeNameIdDto mapToNameIdDto(Employee employee) {
        if (employee == null) return null;
        return new EmployeeNameIdDto(employee.getId(), employee.getName());
    }
}
