package com.hrms.empmanagconsumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hrms.empmanagconsumer.dto.EmployeeDTO;
import com.hrms.empmanagconsumer.exceptions.EntityNotFoundException;

public interface EmployeeService {
    void saveEmployee(EmployeeDTO employeeDTO) throws EntityNotFoundException, JsonProcessingException;
    void updateEmployee(EmployeeDTO employee);
}
