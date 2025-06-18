package com.example.demo.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {

    private Long id;

    private String name;

    private LocalDate creationDate;

    private String headName;

    private List<EmployeeDto> employees;
}
