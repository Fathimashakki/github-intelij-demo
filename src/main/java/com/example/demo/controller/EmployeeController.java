package com.example.demo.controller;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.dto.EmployeeNameIdDto;
import com.example.demo.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // ✅ Create new employee
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto dto) {
        return ResponseEntity.ok(employeeService.createEmployee(dto));
    }

    // ✅ Update existing employee
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id,
                                                      @RequestBody EmployeeDto dto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, dto));
    }

    // ✅ Update employee's department (via EmployeeDto with departmentName)
    @PutMapping("/{id}/department")
    public ResponseEntity<EmployeeDto> changeDepartment(@PathVariable Long id,
                                                        @RequestBody EmployeeDto dto) {
        return ResponseEntity.ok(employeeService.updateEmployeeDepartment(id, dto));
    }

    // ✅ Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }


    @GetMapping
    public ResponseEntity<Page<?>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean lookup) {

        if (Boolean.TRUE.equals(lookup)) {
            Page<EmployeeNameIdDto> result = employeeService.getEmployeeNamesAndIds(page);
            return ResponseEntity.ok(result);
        }

        if (name != null) {
            return ResponseEntity.ok(employeeService.searchEmployees(name, page));
        }

        return ResponseEntity.ok(employeeService.getAllEmployees(page));
    }
}
