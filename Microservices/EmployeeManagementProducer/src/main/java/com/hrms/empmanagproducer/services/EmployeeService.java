package com.hrms.empmanagproducer.services;

import com.hrms.empmanagproducer.dtos.Employee;

public interface EmployeeService {
    void saveEmployee(Employee employee);
    void updateEmployee(Employee employee);
}
