package com.hrms.empmanagconsumer.dao;

import com.hrms.empmanagconsumer.entities.Organization;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationDao extends CrudRepository<Organization, Integer> {
}
