package com.hrms.empmanagproducer.dao;

import com.hrms.empmanagproducer.entities.FailedRecords;
import org.springframework.data.repository.CrudRepository;

public interface FailedRecordDao extends CrudRepository<FailedRecords, Integer> {
}
