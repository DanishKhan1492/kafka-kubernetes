package com.hrms.empmanagconsumer.dao;

import com.hrms.empmanagconsumer.entities.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeDao extends CrudRepository<Employee, Integer> {
}
