package com.iStudent.web;

import com.iStudent.model.DTOs.EmployeeDTO;
import com.iStudent.model.entity.base.BasePersonEntity;
import com.iStudent.service.EmployeeCommandService;
import com.iStudent.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeCommandService employeeCommandService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeCommandService employeeCommandService) {
        this.employeeService = employeeService;
        this.employeeCommandService = employeeCommandService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){
        return ResponseEntity.
                ok(employeeService.getAllEmployees());

    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("id") Long employeeId){

        Optional<EmployeeDTO> employee = employeeService.getEmployeeById(employeeId);

        if (employee.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();

        } else {
            return ResponseEntity
                    .ok(employee.get());

        }
    }

    @GetMapping("/getListByTown/{idTown}")
    public ResponseEntity<EmployeeDTO[]> getListByTown(@PathVariable("idTown") Long idTown) {
        EmployeeDTO[] lista = this.employeeService.getListByTown(idTown);

        if (lista.length == 0) {
            return ResponseEntity
                    .notFound()
                    .build();

        } else {
            return ResponseEntity
                    .ok(lista);
        }
    }


    @PostMapping
    public ResponseEntity<EmployeeDTO> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO,
                                                 UriComponentsBuilder uriComponentsBuilder) {

        long newEmployeeId = employeeService.addEmployee(employeeDTO);
        employeeCommandService.addEmployee(employeeDTO);

        return ResponseEntity
                .created(uriComponentsBuilder.path("/employees/{id}")
                        .build(newEmployeeId))
                .build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeDTO> deleteEmployeeById(@PathVariable("id") Long employeeId) {
        this.employeeService.deleteEmployeeById(employeeId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<EmployeeDTO> changeDepartment(@Valid @RequestBody EmployeeDTO employeeDTO,
                                                    @PathVariable("id") Long id) {

        EmployeeDTO employee = this.employeeService.changeEmployeeDetails(id, employeeDTO);

        if (employee == null) {
            return ResponseEntity
                    .notFound()
                    .build();

        } else {
            return ResponseEntity
                    .ok(employee);
        }
    }
}
