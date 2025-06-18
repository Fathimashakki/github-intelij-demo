package com.example.demo.controller;

import com.example.demo.dto.DepartmentDto;
import com.example.demo.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto dto) {
        return ResponseEntity.ok(departmentService.createDepartment(dto));
    }


    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable Long id,
                                                          @RequestBody DepartmentDto dto) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, dto));
    }

  
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok("Department deleted successfully.");
    }

  
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long id,
                                                           @RequestParam(defaultValue = "false") boolean expand) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id, expand));
    }

 
    @GetMapping
    public ResponseEntity<Page<DepartmentDto>> getAllDepartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String expand) {

        boolean expandEmployees = "employee".equalsIgnoreCase(expand);
        return ResponseEntity.ok(departmentService.getAllDepartments(page, expandEmployees));
    }

}
