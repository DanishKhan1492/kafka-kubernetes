package com.hrms.empmanagconsumer.dao;

import com.hrms.empmanagconsumer.entities.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentDao extends CrudRepository<Department, Integer> {
}
