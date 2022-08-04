package com.hrms.empmanagconsumer.dao;

import com.hrms.empmanagconsumer.entities.EmployeeType;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeTypeDoa extends CrudRepository<EmployeeType, Integer> {
}
