package com.hrms.empmanagproducer.controllers;

import com.hrms.empmanagproducer.dtos.Employee;
import com.hrms.empmanagproducer.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emp")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee){
        employeeService.saveEmployee(employee);
        return new ResponseEntity<>("The Employee account will be created in few seconds. The Employee will get an Email after account is created.", HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateEmployee(@RequestBody Employee employee){
        employeeService.updateEmployee(employee);
        return new ResponseEntity<>("Employee Record will be updated in few seconds.", HttpStatus.OK);
    }

}
